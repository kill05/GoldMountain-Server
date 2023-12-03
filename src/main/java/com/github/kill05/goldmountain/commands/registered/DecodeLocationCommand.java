package com.github.kill05.goldmountain.commands.registered;

import com.github.kill05.goldmountain.commands.Command;
import com.github.kill05.goldmountain.commands.senders.CommandSender;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.utils.Utils;
import io.netty.buffer.Unpooled;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class DecodeLocationCommand extends Command {

    public DecodeLocationCommand() {
        super("decodeloc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        try(PacketSerializer serializer = new PacketSerializer(Unpooled.wrappedBuffer(Hex.decodeHex(args[0])))) {
            sender.sendMessage("Location:" + Utils.vecToString(serializer.readLocation()));
        } catch (DecoderException e) {
            sender.sendMessage("failed to decode location.");
        }
    }

}
