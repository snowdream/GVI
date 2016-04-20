package com.github.snowdream.gvi.lib;

import android.test.AndroidTestCase;

import com.github.snowdream.gvi.lib.entity.Gvi;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;

/**
 * Created by yanghui.yangh on 2016/4/20.
 */
public class GviProcessorTest extends AndroidTestCase {
    public void testFromToJson() {
        InputStream inputStream = null;
        try {
            inputStream = getContext().getAssets().open("test.json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            String json  = sb.toString();

            Gvi gvi = GviProcessor.fromJson(json);

            assertNotNull(gvi);

            String str = GviProcessor.toJson(gvi);
            assertNotNull(str);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ProcessorException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
