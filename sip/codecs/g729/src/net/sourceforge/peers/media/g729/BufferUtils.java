package net.sourceforge.peers.media.g729;

public class BufferUtils {
    /**
     * Copy "copySize" floats from "origBuffer", starting on "startOrigBuffer",
     * to "destBuffer", starting on "startDestBuffer".
     */
    public static int floatBufferIndexedCopy(
        float[] destBuffer,
        int startDestBuffer,
        float[] origBuffer,
        int startOrigBuffer,
        int copySize ) {

        int destBufferIndex = startDestBuffer;
        int origBufferIndex = startOrigBuffer;
        int counter = 0;


        if ( destBuffer.length < ( startDestBuffer + copySize ) ) {
            println( "floatBufferIndexedCopy", "Size copy problem." );
            return -1;
        }

        for ( counter = 0; counter < copySize; counter++ ) {
            destBuffer[ destBufferIndex ] = origBuffer[ origBufferIndex ];

            destBufferIndex++;
            origBufferIndex++;
        }

        println( "floatBufferIndexedCopy", counter + " bytes copied." );

        return counter;
    }


    /**
     * Copy "copySize" bytes from "origBuffer", starting on "startOrigBuffer",
     * to "destBuffer", starting on "startDestBuffer".
     */
    public static int byteBufferIndexedCopy(
        byte[] destBuffer,
        int startDestBuffer,
        byte[] origBuffer,
        int startOrigBuffer,
        int copySize ) {

        int destBufferIndex = startDestBuffer;
        int origBufferIndex = startOrigBuffer;
        int counter = 0;


        if ( destBuffer.length < ( startDestBuffer + copySize ) ) {
            println( "byteBufferIndexedCopy", "size copy problem." );
            return -1;
        }

        for ( counter = 0; counter < copySize; counter++ ) {
            destBuffer[ destBufferIndex ] = origBuffer[ origBufferIndex ];

            destBufferIndex++;
            origBufferIndex++;
        }

        println( "byteBufferIndexedCopy", counter + " bytes copied." );

        return counter;
    }


    private static void println( String method, String message ) {

        //System.out.println( "BufferUtils - " + method + " -> " + message );
    }
}
