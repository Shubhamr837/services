package org.opendatakit.common.android.logic.test;

import android.test.AndroidTestCase;

import org.opendatakit.androidlibrary.R;
import org.opendatakit.common.android.logic.CommonToolProperties;
import org.opendatakit.common.android.logic.PropertiesSingleton;
import org.opendatakit.common.android.utilities.ODKFileUtils;
import org.opendatakit.common.android.utilities.StaticStateManipulator;
import org.opendatakit.common.android.utilities.WebLogger;
import org.opendatakit.common.desktop.WebLoggerDesktopFactoryImpl;

/**
 * @author mitchellsundt@gmail.com
 */
public class PropertiesTest extends AndroidTestCase {

    private static final String APPNAME = "unittestProp";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ODKFileUtils.verifyExternalStorageAvailability();
        ODKFileUtils.assertDirectoryStructure(APPNAME);

        StaticStateManipulator.get().reset();
        WebLogger.setFactory(new WebLoggerDesktopFactoryImpl());
    }

    public void testSimpleProperties() {

        PropertiesSingleton props = CommonToolProperties.get(getContext(), APPNAME);
        // non-default value for font size
        props.setProperty(CommonToolProperties.KEY_FONT_SIZE, "29");
        // these are stored in devices
        props.setProperty(CommonToolProperties.KEY_AUTHENTICATION_TYPE, getContext().getString(R.string.credential_type_google_account));
        props.setProperty(CommonToolProperties.KEY_ACCOUNT, "mitchs.test@gmail.com");
        // this is stored in SharedPreferences
        props.setProperty(CommonToolProperties.KEY_PASSWORD, "asdf");

        StaticStateManipulator.get().reset();

        props = CommonToolProperties.get(getContext(), APPNAME);
        assertEquals(props.getProperty(CommonToolProperties.KEY_FONT_SIZE), "29");
        assertEquals(props.getProperty(CommonToolProperties.KEY_AUTHENTICATION_TYPE),
                getContext().getString(R.string.credential_type_google_account));
        assertEquals(props.getProperty(CommonToolProperties.KEY_ACCOUNT),
                "mitchs.test@gmail.com");
        assertEquals(props.getProperty(CommonToolProperties.KEY_PASSWORD), "asdf");
    }
}