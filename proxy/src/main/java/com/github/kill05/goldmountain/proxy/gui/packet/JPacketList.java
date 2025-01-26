package com.github.kill05.goldmountain.proxy.gui.packet;

import com.github.kill05.goldmountain.connection.packet.Packet;
import com.github.kill05.goldmountain.utils.Utils;
import org.joml.Vector2f;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class JPacketList extends JList<String> {

    private final List<Packet> loggedPackets;
    private final PacketListModel model;

    public JPacketList(PacketPanel packetPanel) {
        this.loggedPackets = new ArrayList<>();
        this.model = new PacketListModel();

        setModel(model);
        setVisibleRowCount(20);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addListSelectionListener(e -> {
            Packet packet = loggedPackets.get(getSelectedIndex());
            StringBuilder builder = new StringBuilder();

            builder.append("Packet ")
                    .append(packet.getClass().getSimpleName())
                    .append("\n\n");

            addPacketInfo(packet, builder);

            packetPanel.getPacketInfoTextArea().setText(builder.toString());
        });
    }

    private void addPacketInfo(Packet packet, StringBuilder builder) {
        Class<?> clazz = packet.getClass();

        while (clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) continue;

                field.setAccessible(true);
                try {
                    Object obj = field.get(packet);

                    builder.append(field.getName())
                            .append(": ")
                            .append(packetFieldToString(obj))
                            .append('\n');
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException("Failed to access field.", ex);
                } finally {
                    field.setAccessible(false);
                }
            }

            clazz = clazz.getSuperclass();
        }
    }

    private String packetFieldToString(Object obj) {
        // Array
        if (obj.getClass().isArray()) {
            StringBuilder builder = new StringBuilder("\n");
            Object[] array = (Object[]) obj;

            for (int i = 0; i < array.length; i++) {
                builder.append("   ")
                        .append(i)
                        .append(": ")
                        .append(packetFieldToString(array[i]));

                if (array.length > i + 1) {
                    builder.append('\n');
                }
            }

            return builder.toString();
        }

        // Vec
        if (obj instanceof Vector2f vec) {
            return Utils.vecToString(vec);
        }

        return obj.toString();
    }


    public void addPacket(Packet packet) {
        loggedPackets.add(packet);
        model.fireContentsChanged();
    }

    public void clear() {
        loggedPackets.clear();
        model.fireContentsChanged();
    }


    private class PacketListModel implements ListModel<String> {

        private final List<ListDataListener> listeners = new ArrayList<>();

        @Override
        public int getSize() {
            return loggedPackets.size();
        }

        @Override
        public String getElementAt(int index) {
            return loggedPackets.get(index).getClass().getSimpleName();
        }

        @Override
        public void addListDataListener(ListDataListener l) {
            listeners.add(l);
        }

        @Override
        public void removeListDataListener(ListDataListener l) {
            listeners.remove(l);
        }

        public void fireContentsChanged() {
            ListDataEvent event = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, getSize());
            for (ListDataListener listener : listeners) {
                listener.contentsChanged(event);
            }
        }
    }
}
