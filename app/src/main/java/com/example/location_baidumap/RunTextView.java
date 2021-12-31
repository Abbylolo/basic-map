package com.example.location_baidumap;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 自定义TextView组件——获取焦点
 * --为跑马灯效果服务。若不设置，当同一布局中有EditText、ScrollView等会自动获取焦点的View时，textView就无法获取焦点，跑马灯效果会失效。
 * @author Abby
 */
public class RunTextView extends androidx.appcompat.widget.AppCompatTextView {

    @Override
    public boolean isFocused() {
        return true;  //强制返回true以获取焦点
    }

    public RunTextView(@NonNull Context context) {
        super(context);
    }

    public RunTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RunTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
