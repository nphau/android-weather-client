package au.com.nab.android.data.common;

import androidx.annotation.StringDef;

@StringDef({HeaderType.LOGGER, HeaderType.AUTHORIZATION, HeaderType.UN_AUTHORIZATION})
public @interface HeaderType {
    String LOGGER = "UN_AUTHORIZATION";
    String AUTHORIZATION = "AUTHORIZATION";
    String UN_AUTHORIZATION = "UN_AUTHORIZATION";
}
