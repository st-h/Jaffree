package com.github.kokorin.jaffree.ffprobe.data;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

public class DataParserTest {

    @Test
    public void parse() throws Exception {
        Data data = parseResource("ffprobe_streams.out");

        Assert.assertNotNull(data);

        List<DSection> streams = data.getSections("STREAM");
        Assert.assertEquals(2, streams.size());
    }

    @Test
    public void parseHttpsPresigned() throws Exception {
        Data data = parseResource("ffprobe_streams_presigned_https.out");

        Assert.assertNotNull(data);

        List<DSection> streams = data.getSections("STREAM");
        Assert.assertEquals(2, streams.size());

        DSection format = data.getSection("FORMAT");
        Assert.assertNotNull(format);

        String filename = format.getString("filename");
        String expectedFilename = "https://somebucket.s3.region.amazonaws.com/file.mp4?versionId=v1&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=somedate&X-Amz-SignedHeaders=host&X-Amz-Expires=someamount&X-Amz-Credential=cred&X-Amz-Signature=sig";
        Assert.assertEquals(expectedFilename, filename);
    }

    public static Data parseResource(String name) throws Exception {
        try (InputStream input = DataParserTest.class.getResourceAsStream(name)){
            Iterator<String> lineIterator = IOUtils.lineIterator(input, StandardCharsets.UTF_8);
            return DataParser.parse(lineIterator);
        }
    }
}