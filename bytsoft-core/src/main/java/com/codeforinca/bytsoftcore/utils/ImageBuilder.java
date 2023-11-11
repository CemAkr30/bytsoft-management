package com.codeforinca.bytsoftcore.utils;

import java.util.Base64;

public class ImageBuilder {


        public
        static
        String
        encodeImage(
                byte[] imageBytes
        ) {
            return Base64.getEncoder()
                    .encodeToString(
                            imageBytes
                    );
        }

        public
        static
        byte[]
        decodeImage(
                String imageString
        ){
            return Base64.getDecoder()
                    .decode(
                            imageString
                    );
        }

}
