package com.github.kill05.goldmountain.connection.packet;

import com.github.kill05.goldmountain.connection.PacketBuffer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class PacketRegistry {

    private final Map<Integer, RegisteredPacket<?>> packetMap;
    private final Map<Class<? extends Packet>, RegisteredPacket<?>> packetClassMap;

    public PacketRegistry() {
        this.packetMap = new HashMap<>();
        this.packetClassMap = new HashMap<>();
    }


    /**
     * Registers a packet that is both inbound and outbound.
     *
     * @param id the id of the packet
     * @param clazz the class of the packet
     * @param encoder the serializer
     * @param decoder the deserializer
     * @param <T> the packet type
     */
    public <T extends Packet> void registerIOPacket(
            int id,
            @NotNull Class<T> clazz,
            @NotNull BiConsumer<PacketBuffer, T> encoder,
            @NotNull Function<PacketBuffer, T> decoder
    ) {
        if (packetMap.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Duplicate inbound packet id: '%02x'", id));
        }
        registerPacket(new RegisteredPacket<T>(id, clazz, encoder, decoder));
    }

    /**
     * Registers an outbound packet.
     *
     * @param id the id of the packet
     * @param clazz the class of the packet
     * @param encoder the serializer
     * @param <T> the packet type
     */
    public <T extends Packet> void registerOutboundPacket(
            int id,
            @NotNull Class<T> clazz,
            @NotNull BiConsumer<PacketBuffer, T> encoder
    ) {
        registerPacket(new RegisteredPacket<>(id, clazz, encoder, null));
    }

    /**
     * Registers a packet
     *
     * @param packet the registered packet
     * @param <T> the packet type
     */
    private <T extends Packet> void registerPacket(RegisteredPacket<T> packet) {
        int id = packet.id();

        if (packetMap.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Duplicate inbound packet id: '%02x'", id));
        }

        packetMap.put(id, packet);
        packetClassMap.put(packet.clazz(), packet);
    }

    /**
     * Gets a registered packet from its id
     *
     * @param id the id
     * @return the registered packet
     * @throws IllegalArgumentException if the packet id is invalid
     */
    @NotNull
    public RegisteredPacket<?> getRegisteredPacket(int id) throws IllegalArgumentException {
        RegisteredPacket<?> registeredPacket = packetMap.get(id);

        if (registeredPacket == null) {
            throw new IllegalArgumentException(String.format("Invalid packet id: '%02x'", id));
        }

        return registeredPacket;
    }

    /**
     * Gets a registered packet from its class
     *
     * @param packet the packet class
     * @return the registered packet
     * @throws IllegalArgumentException if the packet class is not registered
     */
    public RegisteredPacket<?> getRegisteredPacket(Class<? extends Packet> packet) throws IllegalArgumentException {
        RegisteredPacket<?> registeredPacket = packetClassMap.get(packet);

        if (registeredPacket == null) {
            throw new IllegalArgumentException(String.format("Invalid packet class: '%s'", packet));
        }

        return registeredPacket;
    }

    /**
     * Decodes a packet from an id and the buffer containing its data.
     *
     * @param id the id of the packet
     * @param buf the buffer with the packet data
     * @return the packet object
     * @throws IOException if an error occurred while decoding the packet
     */
    public Packet decodePacket(int id, PacketBuffer buf) throws IOException {
        try {
            Function<PacketBuffer, ?> decoder = getRegisteredPacket(id).decoder();

            if (decoder == null) {
                throw new IllegalArgumentException(String.format("Packet '%02x' is not an inbound packet.", id));
            }

            return (Packet) decoder.apply(buf);
        } catch (Exception e) {
            throw new IOException("Failed to decode packet.", e);
        }
    }

    /**
     * Writes a packet's id and data to the serializer.
     *
     * @param buf the packet buffer
     * @param packet the packet to encode
     * @throws IOException if an error occurred while encoding the packet
     */
    @SuppressWarnings("unchecked")
    public void encodePacket(PacketBuffer buf, Packet packet) throws IOException {
        RegisteredPacket<Packet> registeredPacket = (RegisteredPacket<Packet>) getRegisteredPacket(packet.getClass());
        buf.writeByte(registeredPacket.id());

        try {
            registeredPacket.encoder().accept(buf, packet);
        } catch (Exception e) {
            throw new IOException("Failed to encode packet.", e);
        }
    }

    /**
     * Represents a registered packet.
     *
     * @param id the id
     * @param clazz the class
     * @param encoder the encoder
     * @param decoder the decoder. Null for outbound packets
     * @param <T> the packet type
     */
    public record RegisteredPacket<T extends Packet>(
            int id,
            @NotNull Class<T> clazz,
            @NotNull BiConsumer<PacketBuffer, T> encoder,
            @Nullable Function<PacketBuffer, T> decoder
    ) {

        /**
         * Check if a packet is outbound only, ie it can only be sent by the server and can't be received.
         * @return true if packet is server only
         */
        public boolean isOutboundOnly() {
            return decoder == null;
        }
    }
}
