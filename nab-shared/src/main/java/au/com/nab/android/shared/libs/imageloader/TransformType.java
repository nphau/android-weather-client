package au.com.nab.android.shared.libs.imageloader;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({TransformType.NORMAL, TransformType.CIRCLE,
        TransformType.FIT_CENTER, TransformType.ROUND_CORNER,
        TransformType.AVATAR})
public @interface TransformType {
    int NORMAL = 0;
    int CIRCLE = 1;
    int FIT_CENTER = 2;
    int ROUND_CORNER = 3;
    int AVATAR = 4;
}
