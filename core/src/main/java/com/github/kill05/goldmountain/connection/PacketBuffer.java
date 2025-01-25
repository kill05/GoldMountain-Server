package com.github.kill05.goldmountain.connection;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ByteProcessor;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

public class PacketBuffer extends ByteBuf implements AutoCloseable {

    public static final float TILE_SIZE = 32f;

    private final ByteBuf byteBuf;

    public PacketBuffer(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }


    public Vector2f readLocation() {
        return new Vector2f(readCoordinate(), readCoordinate());
    }

    public float readCoordinate() {
        return byteBuf.readShortLE() / TILE_SIZE;
    }
    
    public void writeLocation(@Nullable Vector2f location) {
        float x = location != null ? location.x : 0f;
        float y = location != null ? location.y : 0f;
        writeLocation(x, y);
    }

    public void writeLocation(float x, float y) {
        byteBuf.writeShortLE((int) (x * TILE_SIZE));
        byteBuf.writeShortLE((int) (y * TILE_SIZE));
    }


    @Override
    public int capacity() {
        return byteBuf.capacity();
    }

    @Override
    public PacketBuffer capacity(int i) {
        byteBuf.capacity(i);
        return this;
    }

    @Override
    public int maxCapacity() {
        return byteBuf.maxCapacity();
    }

    @Override
    public ByteBufAllocator alloc() {
        return byteBuf.alloc();
    }

    @Override
    @Deprecated
    public ByteOrder order() {
        return byteBuf.order();
    }

    @Override
    @Deprecated
    public PacketBuffer order(ByteOrder byteOrder) {
        byteBuf.order(byteOrder);
        return this;
    }

    @Override
    public PacketBuffer unwrap() {
        byteBuf.unwrap();
        return this;
    }

    @Override
    public boolean isDirect() {
        return byteBuf.isDirect();
    }

    @Override
    public boolean isReadOnly() {
        return byteBuf.isReadOnly();
    }

    @Override
    public PacketBuffer asReadOnly() {
        byteBuf.asReadOnly();
        return this;
    }

    @Override
    public int readerIndex() {
        return byteBuf.readerIndex();
    }

    @Override
    public PacketBuffer readerIndex(int i) {
        byteBuf.readerIndex(i);
        return this;
    }

    @Override
    public int writerIndex() {
        return byteBuf.writerIndex();
    }

    @Override
    public PacketBuffer writerIndex(int i) {
        byteBuf.writerIndex(i);
        return this;
    }

    @Override
    public PacketBuffer setIndex(int i, int i1) {
        byteBuf.setIndex(i, i1);
        return this;
    }

    @Override
    public int readableBytes() {
        return byteBuf.readableBytes();
    }

    @Override
    public int writableBytes() {
        return byteBuf.writableBytes();
    }

    @Override
    public int maxWritableBytes() {
        return byteBuf.maxWritableBytes();
    }

    @Override
    public int maxFastWritableBytes() {
        return byteBuf.maxFastWritableBytes();
    }

    @Override
    public boolean isReadable() {
        return byteBuf.isReadable();
    }

    @Override
    public boolean isReadable(int i) {
        return byteBuf.isReadable(i);
    }

    @Override
    public boolean isWritable() {
        return byteBuf.isWritable();
    }

    @Override
    public boolean isWritable(int i) {
        return byteBuf.isWritable(i);
    }

    @Override
    public PacketBuffer clear() {
        byteBuf.clear();
        return this;
    }

    @Override
    public PacketBuffer markReaderIndex() {
        byteBuf.markReaderIndex();
        return this;
    }

    @Override
    public PacketBuffer resetReaderIndex() {
        byteBuf.resetReaderIndex();
        return this;
    }

    @Override
    public PacketBuffer markWriterIndex() {
        byteBuf.markWriterIndex();
        return this;
    }

    @Override
    public PacketBuffer resetWriterIndex() {
        byteBuf.resetWriterIndex();
        return this;
    }

    @Override
    public PacketBuffer discardReadBytes() {
        byteBuf.discardReadBytes();
        return this;
    }

    @Override
    public PacketBuffer discardSomeReadBytes() {
        byteBuf.discardSomeReadBytes();
        return this;
    }

    @Override
    public PacketBuffer ensureWritable(int i) {
        byteBuf.ensureWritable(i);
        return this;
    }

    @Override
    public int ensureWritable(int i, boolean b) {
        return byteBuf.ensureWritable(i, b);
    }

    @Override
    public boolean getBoolean(int i) {
        return byteBuf.getBoolean(i);
    }

    @Override
    public byte getByte(int i) {
        return byteBuf.getByte(i);
    }

    @Override
    public short getUnsignedByte(int i) {
        return byteBuf.getUnsignedByte(i);
    }

    @Override
    public short getShort(int i) {
        return byteBuf.getShort(i);
    }

    @Override
    public short getShortLE(int i) {
        return byteBuf.getShortLE(i);
    }

    @Override
    public int getUnsignedShort(int i) {
        return byteBuf.getUnsignedShort(i);
    }

    @Override
    public int getUnsignedShortLE(int i) {
        return byteBuf.getUnsignedShortLE(i);
    }

    @Override
    public int getMedium(int i) {
        return byteBuf.getMedium(i);
    }

    @Override
    public int getMediumLE(int i) {
        return byteBuf.getMediumLE(i);
    }

    @Override
    public int getUnsignedMedium(int i) {
        return byteBuf.getUnsignedMedium(i);
    }

    @Override
    public int getUnsignedMediumLE(int i) {
        return byteBuf.getUnsignedMediumLE(i);
    }

    @Override
    public int getInt(int i) {
        return byteBuf.getInt(i);
    }

    @Override
    public int getIntLE(int i) {
        return byteBuf.getIntLE(i);
    }

    @Override
    public long getUnsignedInt(int i) {
        return byteBuf.getUnsignedInt(i);
    }

    @Override
    public long getUnsignedIntLE(int i) {
        return byteBuf.getUnsignedIntLE(i);
    }

    @Override
    public long getLong(int i) {
        return byteBuf.getLong(i);
    }

    @Override
    public long getLongLE(int i) {
        return byteBuf.getLongLE(i);
    }

    @Override
    public char getChar(int i) {
        return byteBuf.getChar(i);
    }

    @Override
    public float getFloat(int i) {
        return byteBuf.getFloat(i);
    }

    @Override
    public float getFloatLE(int index) {
        return byteBuf.getFloatLE(index);
    }

    @Override
    public double getDouble(int i) {
        return byteBuf.getDouble(i);
    }

    @Override
    public double getDoubleLE(int index) {
        return byteBuf.getDoubleLE(index);
    }

    @Override
    public PacketBuffer getBytes(int i, ByteBuf byteBuf) {
        this.byteBuf.getBytes(i, byteBuf);
        return this;
    }

    @Override
    public PacketBuffer getBytes(int i, ByteBuf byteBuf, int i1) {
        this.byteBuf.getBytes(i, byteBuf, i1);
        return this;
    }

    @Override
    public PacketBuffer getBytes(int i, ByteBuf byteBuf, int i1, int i2) {
        this.byteBuf.getBytes(i, byteBuf, i1, i2);
        return this;
    }

    @Override
    public PacketBuffer getBytes(int i, byte[] bytes) {
        byteBuf.getBytes(i, bytes);
        return this;
    }

    @Override
    public PacketBuffer getBytes(int i, byte[] bytes, int i1, int i2) {
        byteBuf.getBytes(i, bytes, i1, i2);
        return this;
    }

    @Override
    public PacketBuffer getBytes(int i, OutputStream outputStream, int i1) throws IOException {
        byteBuf.getBytes(i, outputStream, i1);
        return this;
    }

    @Override
    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i1) throws IOException {
        return byteBuf.getBytes(i, gatheringByteChannel, i1);
    }

    @Override
    public int getBytes(int i, FileChannel fileChannel, long l, int i1) throws IOException {
        return byteBuf.getBytes(i, fileChannel, l, i1);
    }

    @Override
    public PacketBuffer getBytes(int i, ByteBuffer byteBuffer) {
        byteBuf.getBytes(i, byteBuffer);
        return this;
    }

    @Override
    public CharSequence getCharSequence(int i, int i1, Charset charset) {
        return byteBuf.getCharSequence(i, i1, charset);
    }

    @Override
    public PacketBuffer setBoolean(int i, boolean b) {
        byteBuf.setBoolean(i, b);
        return this;
    }

    @Override
    public PacketBuffer setByte(int i, int i1) {
        byteBuf.setByte(i, i1);
        return this;
    }

    @Override
    public PacketBuffer setShort(int i, int i1) {
        byteBuf.setShort(i, i1);
        return this;
    }

    @Override
    public PacketBuffer setShortLE(int i, int i1) {
        byteBuf.setShortLE(i, i1);
        return this;
    }

    @Override
    public PacketBuffer setMedium(int i, int i1) {
        byteBuf.setMedium(i, i1);
        return this;
    }

    @Override
    public PacketBuffer setMediumLE(int i, int i1) {
        byteBuf.setMediumLE(i, i1);
        return this;
    }

    @Override
    public PacketBuffer setInt(int i, int i1) {
        byteBuf.setInt(i, i1);
        return this;
    }

    @Override
    public PacketBuffer setIntLE(int i, int i1) {
        byteBuf.setIntLE(i, i1);
        return this;
    }

    @Override
    public PacketBuffer setLong(int i, long l) {
        byteBuf.setLong(i, l);
        return this;
    }

    @Override
    public PacketBuffer setLongLE(int i, long l) {
        byteBuf.setLongLE(i, l);
        return this;
    }

    @Override
    public PacketBuffer setChar(int i, int i1) {
        byteBuf.setChar(i, i1);
        return this;
    }

    @Override
    public PacketBuffer setFloat(int i, float v) {
        byteBuf.setFloat(i, v);
        return this;
    }

    @Override
    public PacketBuffer setFloatLE(int index, float value) {
        byteBuf.setFloatLE(index, value);
        return this;
    }

    @Override
    public PacketBuffer setDouble(int i, double v) {
        byteBuf.setDouble(i, v);
        return this;
    }

    @Override
    public PacketBuffer setDoubleLE(int index, double value) {
        byteBuf.setDoubleLE(index, value);
        return this;
    }

    @Override
    public PacketBuffer setBytes(int i, ByteBuf byteBuf) {
        this.byteBuf.setBytes(i, byteBuf);
        return this;
    }

    @Override
    public PacketBuffer setBytes(int i, ByteBuf byteBuf, int i1) {
        this.byteBuf.setBytes(i, byteBuf, i1);
        return this;
    }

    @Override
    public PacketBuffer setBytes(int i, ByteBuf byteBuf, int i1, int i2) {
        this.byteBuf.setBytes(i, byteBuf, i1, i2);
        return this;
    }

    @Override
    public PacketBuffer setBytes(int i, byte[] bytes) {
        byteBuf.setBytes(i, bytes);
        return this;
    }

    @Override
    public PacketBuffer setBytes(int i, byte[] bytes, int i1, int i2) {
        byteBuf.setBytes(i, bytes, i1, i2);
        return this;
    }

    @Override
    public int setBytes(int i, InputStream inputStream, int i1) throws IOException {
        return byteBuf.setBytes(i, inputStream, i1);
    }

    @Override
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i1) throws IOException {
        return byteBuf.setBytes(i, scatteringByteChannel, i1);
    }

    @Override
    public int setBytes(int i, FileChannel fileChannel, long l, int i1) throws IOException {
        return byteBuf.setBytes(i, fileChannel, l, i1);
    }

    @Override
    public PacketBuffer setBytes(int i, ByteBuffer byteBuffer) {
        this.byteBuf.setBytes(i, byteBuffer);
        return this;
    }

    @Override
    public PacketBuffer setZero(int i, int i1) {
        byteBuf.setZero(i, i1);
        return this;
    }

    @Override
    public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
        return byteBuf.setCharSequence(i, charSequence, charset);
    }

    @Override
    public boolean readBoolean() {
        return byteBuf.readBoolean();
    }

    @Override
    public byte readByte() {
        return byteBuf.readByte();
    }

    @Override
    public short readUnsignedByte() {
        return byteBuf.readUnsignedByte();
    }

    @Override
    public short readShort() {
        return byteBuf.readShort();
    }

    @Override
    public short readShortLE() {
        return byteBuf.readShortLE();
    }

    @Override
    public int readUnsignedShort() {
        return byteBuf.readUnsignedShort();
    }

    @Override
    public int readUnsignedShortLE() {
        return byteBuf.readUnsignedShortLE();
    }

    @Override
    public int readMedium() {
        return byteBuf.readMedium();
    }

    @Override
    public int readMediumLE() {
        return byteBuf.readMediumLE();
    }

    @Override
    public int readUnsignedMedium() {
        return byteBuf.readUnsignedMedium();
    }

    @Override
    public int readUnsignedMediumLE() {
        return byteBuf.readUnsignedMediumLE();
    }

    @Override
    public int readInt() {
        return byteBuf.readInt();
    }

    @Override
    public int readIntLE() {
        return byteBuf.readIntLE();
    }

    @Override
    public long readUnsignedInt() {
        return byteBuf.readUnsignedInt();
    }

    @Override
    public long readUnsignedIntLE() {
        return byteBuf.readUnsignedIntLE();
    }

    @Override
    public long readLong() {
        return byteBuf.readLong();
    }

    @Override
    public long readLongLE() {
        return byteBuf.readLongLE();
    }

    @Override
    public char readChar() {
        return byteBuf.readChar();
    }

    @Override
    public float readFloat() {
        return byteBuf.readFloat();
    }

    @Override
    public float readFloatLE() {
        return byteBuf.readFloatLE();
    }

    @Override
    public double readDouble() {
        return byteBuf.readDouble();
    }

    @Override
    public double readDoubleLE() {
        return byteBuf.readDoubleLE();
    }

    @Override
    public PacketBuffer readBytes(int i) {
        byteBuf.readBytes(i);
        return this;
    }

    @Override
    public PacketBuffer readSlice(int i) {
        byteBuf.readSlice(i);
        return this;
    }

    @Override
    public PacketBuffer readRetainedSlice(int i) {
        byteBuf.readRetainedSlice(i);
        return this;
    }

    @Override
    public PacketBuffer readBytes(ByteBuf byteBuf) {
        this.byteBuf.readBytes(byteBuf);
        return this;
    }

    @Override
    public PacketBuffer readBytes(ByteBuf byteBuf, int i) {
        this.byteBuf.readBytes(byteBuf, i);
        return this;
    }

    @Override
    public PacketBuffer readBytes(ByteBuf byteBuf, int i, int i1) {
        this.byteBuf.readBytes(byteBuf, i, i1);
        return this;
    }

    @Override
    public PacketBuffer readBytes(byte[] bytes) {
        byteBuf.readBytes(bytes);
        return this;
    }

    @Override
    public PacketBuffer readBytes(byte[] bytes, int i, int i1) {
        byteBuf.readBytes(bytes, i, i1);
        return this;
    }

    @Override
    public PacketBuffer readBytes(ByteBuffer byteBuffer) {
        byteBuf.readBytes(byteBuffer);
        return this;
    }

    @Override
    public PacketBuffer readBytes(OutputStream outputStream, int i) throws IOException {
        byteBuf.readBytes(outputStream, i);
        return this;
    }

    @Override
    public int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
        return byteBuf.readBytes(gatheringByteChannel, i);
    }

    @Override
    public CharSequence readCharSequence(int i, Charset charset) {
        return byteBuf.readCharSequence(i, charset);
    }

    @Override
    public int readBytes(FileChannel fileChannel, long l, int i) throws IOException {
        return byteBuf.readBytes(fileChannel, l, i);
    }

    @Override
    public PacketBuffer skipBytes(int i) {
        byteBuf.skipBytes(i);
        return this;
    }

    @Override
    public PacketBuffer writeBoolean(boolean b) {
        byteBuf.writeBoolean(b);
        return this;
    }

    @Override
    public PacketBuffer writeByte(int i) {
        byteBuf.writeByte(i);
        return this;
    }

    @Override
    public PacketBuffer writeShort(int i) {
        byteBuf.writeShort(i);
        return this;
    }

    @Override
    public PacketBuffer writeShortLE(int i) {
        byteBuf.writeShortLE(i);
        return this;
    }

    @Override
    public PacketBuffer writeMedium(int i) {
        byteBuf.writeMedium(i);
        return this;
    }

    @Override
    public PacketBuffer writeMediumLE(int i) {
        byteBuf.writeMediumLE(i);
        return this;
    }

    @Override
    public PacketBuffer writeInt(int i) {
        byteBuf.writeInt(i);
        return this;
    }

    @Override
    public PacketBuffer writeIntLE(int i) {
        byteBuf.writeIntLE(i);
        return this;
    }

    @Override
    public PacketBuffer writeLong(long l) {
        byteBuf.writeLong(l);
        return this;
    }

    @Override
    public PacketBuffer writeLongLE(long l) {
        byteBuf.writeLongLE(l);
        return this;
    }

    @Override
    public PacketBuffer writeChar(int i) {
        byteBuf.writeChar(i);
        return this;
    }

    @Override
    public PacketBuffer writeFloat(float v) {
        byteBuf.writeFloat(v);
        return this;
    }

    @Override
    public PacketBuffer writeFloatLE(float value) {
        byteBuf.writeFloatLE(value);
        return this;
    }

    @Override
    public PacketBuffer writeDouble(double v) {
        byteBuf.writeDouble(v);
        return this;
    }

    @Override
    public PacketBuffer writeDoubleLE(double value) {
        byteBuf.writeDoubleLE(value);
        return this;
    }

    @Override
    public PacketBuffer writeBytes(ByteBuf byteBuf) {
        this.byteBuf.writeBytes(byteBuf);
        return this;
    }

    @Override
    public PacketBuffer writeBytes(ByteBuf byteBuf, int i) {
        this.byteBuf.writeBytes(byteBuf, i);
        return this;
    }

    @Override
    public PacketBuffer writeBytes(ByteBuf byteBuf, int i, int i1) {
        this.byteBuf.writeBytes(byteBuf, i, i1);
        return this;
    }

    @Override
    public PacketBuffer writeBytes(byte[] bytes) {
        byteBuf.writeBytes(bytes);
        return this;
    }

    @Override
    public PacketBuffer writeBytes(byte[] bytes, int i, int i1) {
        byteBuf.writeBytes(bytes, i, i1);
        return this;
    }

    @Override
    public PacketBuffer writeBytes(ByteBuffer byteBuffer) {
        byteBuf.writeBytes(byteBuffer);
        return this;
    }

    @Override
    public int writeBytes(InputStream inputStream, int i) throws IOException {
        return byteBuf.writeBytes(inputStream, i);
    }

    @Override
    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) throws IOException {
        return byteBuf.writeBytes(scatteringByteChannel, i);
    }

    @Override
    public int writeBytes(FileChannel fileChannel, long l, int i) throws IOException {
        return byteBuf.writeBytes(fileChannel, l, i);
    }

    @Override
    public PacketBuffer writeZero(int i) {
        byteBuf.writeZero(i);
        return this;
    }

    @Override
    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        return byteBuf.writeCharSequence(charSequence, charset);
    }

    @Override
    public int indexOf(int i, int i1, byte b) {
        return byteBuf.indexOf(i, i1, b);
    }

    @Override
    public int bytesBefore(byte b) {
        return byteBuf.bytesBefore(b);
    }

    @Override
    public int bytesBefore(int i, byte b) {
        return byteBuf.bytesBefore(i, b);
    }

    @Override
    public int bytesBefore(int i, int i1, byte b) {
        return byteBuf.bytesBefore(i, i1, b);
    }

    @Override
    public int forEachByte(ByteProcessor byteProcessor) {
        return byteBuf.forEachByte(byteProcessor);
    }

    @Override
    public int forEachByte(int i, int i1, ByteProcessor byteProcessor) {
        return byteBuf.forEachByte(i, i1, byteProcessor);
    }

    @Override
    public int forEachByteDesc(ByteProcessor byteProcessor) {
        return byteBuf.forEachByteDesc(byteProcessor);
    }

    @Override
    public int forEachByteDesc(int i, int i1, ByteProcessor byteProcessor) {
        return byteBuf.forEachByteDesc(i, i1, byteProcessor);
    }

    @Override
    public PacketBuffer copy() {
        return new PacketBuffer(byteBuf.copy());
    }

    @Override
    public PacketBuffer copy(int i, int i1) {
        return new PacketBuffer(byteBuf.copy(i, i1));
    }

    @Override
    public PacketBuffer slice() {
        return new PacketBuffer(byteBuf.slice());
    }

    @Override
    public PacketBuffer retainedSlice() {
        return new PacketBuffer(byteBuf.retainedSlice());
    }

    @Override
    public PacketBuffer slice(int i, int i1) {
        return new PacketBuffer(byteBuf.slice(i, i1));
    }

    @Override
    public PacketBuffer retainedSlice(int i, int i1) {
        return new PacketBuffer(byteBuf.retainedSlice(i, i1));
    }

    @Override
    public PacketBuffer duplicate() {
        return new PacketBuffer(byteBuf.duplicate());
    }

    @Override
    public PacketBuffer retainedDuplicate() {
        return new PacketBuffer(byteBuf.retainedDuplicate());
    }

    @Override
    public int nioBufferCount() {
        return byteBuf.nioBufferCount();
    }

    @Override
    public ByteBuffer nioBuffer() {
        return byteBuf.nioBuffer();
    }

    @Override
    public ByteBuffer nioBuffer(int i, int i1) {
        return byteBuf.nioBuffer(i, i1);
    }

    @Override
    public ByteBuffer internalNioBuffer(int i, int i1) {
        return byteBuf.internalNioBuffer(i, i1);
    }

    @Override
    public ByteBuffer[] nioBuffers() {
        return byteBuf.nioBuffers();
    }

    @Override
    public ByteBuffer[] nioBuffers(int i, int i1) {
        return byteBuf.nioBuffers(i, i1);
    }

    @Override
    public boolean hasArray() {
        return byteBuf.hasArray();
    }

    @Override
    public byte[] array() {
        return byteBuf.array();
    }

    @Override
    public int arrayOffset() {
        return byteBuf.arrayOffset();
    }

    @Override
    public boolean hasMemoryAddress() {
        return byteBuf.hasMemoryAddress();
    }

    @Override
    public long memoryAddress() {
        return byteBuf.memoryAddress();
    }

    @Override
    public boolean isContiguous() {
        return byteBuf.isContiguous();
    }

    @Override
    public ByteBuf asByteBuf() {
        return byteBuf.asByteBuf();
    }

    @Override
    public String toString(Charset charset) {
        return byteBuf.toString(charset);
    }

    @Override
    public String toString(int i, int i1, Charset charset) {
        return byteBuf.toString(i, i1, charset);
    }

    @Override
    public int hashCode() {
        return byteBuf.hashCode();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) {
        return byteBuf.equals(o);
    }

    @Override
    public int compareTo(ByteBuf byteBuf) {
        return this.byteBuf.compareTo(byteBuf);
    }

    @Override
    public String toString() {
        return byteBuf.toString();
    }

    @Override
    public PacketBuffer retain(int i) {
        byteBuf.retain(i);
        return this;
    }

    @Override
    public PacketBuffer retain() {
        byteBuf.retain();
        return this;
    }

    @Override
    public PacketBuffer touch() {
        byteBuf.touch();
        return this;
    }

    @Override
    public PacketBuffer touch(Object o) {
        byteBuf.touch(o);
        return this;
    }

    @Override
    public int refCnt() {
        return byteBuf.refCnt();
    }

    @Override
    public boolean release() {
        return byteBuf.release();
    }

    @Override
    public boolean release(int i) {
        return byteBuf.release(i);
    }


    @Override
    public void close() {
        release();
    }
}