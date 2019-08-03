package com.example.caydecompanion;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.test.InstrumentationRegistry;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.destiny_api_client.utils.ConfigConstants;
import com.example.destiny_api_client.utils.ConfigMetadata;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EnvironmentVariablePlaceholderTest {

    @Test
    public void test() throws PackageManager.NameNotFoundException {
        // Context of the app under test.
        Context appContext = ApplicationProvider.getApplicationContext();

        final String clientId = new ConfigMetadata(appContext).getData(ConfigConstants.CLIENT_ID);
        assertEquals("ABC_123", clientId);
    }
}
