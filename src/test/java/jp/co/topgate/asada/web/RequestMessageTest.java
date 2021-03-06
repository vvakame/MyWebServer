package jp.co.topgate.asada.web;

import jp.co.topgate.asada.web.exception.RequestParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by yusuke-pc on 2017/04/13.
 */
@RunWith(Enclosed.class)
public class RequestMessageTest {
    public static class コンストラクタのテスト {
        @Test
        public void nullチェック() throws Exception {
            File file = new File("./src/test/resources/empty.txt");
            try (InputStream is = new FileInputStream(file)) {
                RequestMessage sut = new RequestMessage(is);
            } catch (RequestParseException e) {
                assertThat(e.getMessage(), is("不正なリクエストメッセージをパースしようとしました"));
            }
        }

        @Test
        public void パースのテスト() throws Exception {
            try (InputStream is = new FileInputStream(new File("./src/test/resources/requestMessage.txt"))) {
                RequestMessage sut = new RequestMessage(is);

                assertThat(sut.getMethod(), is("GET"));
                assertThat(sut.getUri(), is("/index.html"));
                assertThat(sut.getProtocolVersion(), is("HTTP/1.1"));
                assertThat(sut.findHeaderByName("Host"), is("localhost:8080"));
                assertThat(sut.findUriQuery("name"), is("asada"));
                assertThat(sut.findUriQuery("like"), is("cat"));
            }
        }

        @Test
        public void POSTでメッセージボディに何か入れてみる() throws Exception {
            try (InputStream is = new FileInputStream(new File("./src/test/resources/PostRequestMessage.txt"));) {
                RequestMessage sut = new RequestMessage(is);

                assertThat(sut.getMethod(), is("POST"));
                assertThat(sut.getUri(), is("/index.html"));
                assertThat(sut.getProtocolVersion(), is("HTTP/1.1"));
                assertThat(sut.findHeaderByName("Host"), is("localhost:8080"));
                assertThat(sut.findMessageBody("name"), is("asada"));
                assertThat(sut.findMessageBody("like"), is("cat"));
            }
        }
    }

    public static class methodのテスト {
        @Test
        public void 正しく値を得られるか() throws Exception {
            InputStream is = new FileInputStream(new File("./src/test/resources/requestMessage.txt"));
            RequestMessage sut = new RequestMessage(is);
            assertThat(sut.getMethod(), is("GET"));
        }
    }

    public static class URIのテスト {
        @Test
        public void 正しく値を得られるか() throws Exception {
            InputStream is = new FileInputStream(new File("./src/test/resources/requestMessage.txt"));
            RequestMessage sut = new RequestMessage(is);
            assertThat(sut.getUri(), is("/index.html"));
        }
    }

    public static class URIのクエリーのテスト {
        RequestMessage sut;

        @Before
        public void setUp() throws Exception {
            InputStream is = new FileInputStream(new File("./src/test/resources/requestMessage.txt"));
            sut = new RequestMessage(is);
        }

        @Test
        public void nullチェック() {
            assertThat(sut.findUriQuery(null), is(nullValue()));
        }

        @Test
        public void 正しく値を得られるか() {
            assertThat(sut.findUriQuery("name"), is("asada"));
        }
    }

    public static class プロトコルバージョンのテスト {
        @Test
        public void 正しく値を得られるか() throws Exception {
            InputStream is = new FileInputStream(new File("./src/test/resources/requestMessage.txt"));
            RequestMessage sut = new RequestMessage(is);
            assertThat(sut.getProtocolVersion(), is("HTTP/1.1"));
        }
    }

    public static class ヘッダーフィルードのテスト {
        RequestMessage sut;

        @Before
        public void setUp() throws Exception {
            InputStream is = new FileInputStream(new File("./src/test/resources/requestMessage.txt"));
            sut = new RequestMessage(is);
        }

        @Test
        public void nullチェック() throws Exception {
            assertThat(sut.findHeaderByName(null), is(nullValue()));
        }

        @Test
        public void 正しく値を得られるか() throws Exception {
            assertThat(sut.findHeaderByName("Host"), is("localhost:8080"));
        }
    }

    public static class メッセージボディのテスト {
        RequestMessage sut;

        @Before
        public void setUp() throws Exception {
            InputStream is = new FileInputStream(new File("./src/test/resources/PostRequestMessage.txt"));
            sut = new RequestMessage(is);
        }

        @Test
        public void nullチェック() throws Exception {
            assertThat(sut.findMessageBody(null), is(nullValue()));
        }

        @Test
        public void 正しく値を得られるか() throws Exception {
            assertThat(sut.findMessageBody("like"), is("cat"));
        }
    }
}
