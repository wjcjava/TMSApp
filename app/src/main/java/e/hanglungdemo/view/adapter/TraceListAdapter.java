package e.hanglungdemo.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import e.hanglungdemo.MainActivity;
import e.hanglungdemo.R;
import e.hanglungdemo.utils.SpannableStringUtils;
import e.hanglungdemo.view.bean.TraceBean;
import e.hanglungdemo.view.home.activity.SignInActivity;
import e.library.BaseDialog;
import e.library.T;

public class TraceListAdapter extends BaseAdapter {
    private Context context;
    private List<TraceBean> traceBeanList;
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL = 0x0001;
    private Intent intent;
    private BaseDialog dialog;

    public TraceListAdapter(Context context, List<TraceBean> traceBeanList) {
        this.context = context;
        this.traceBeanList = traceBeanList;
    }

    @Override
    public int getCount() {
        return traceBeanList.size();
    }

    @Override
    public TraceBean getItem(int position) {
        return traceBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final TraceBean traceBean = getItem(position);
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_trace, parent, false);
            holder.tvAcceptTime = convertView.findViewById(R.id.tvAcceptTime);
            holder.tvAcceptStation = convertView.findViewById(R.id.tvAcceptStation);
            holder.tvTopLine = convertView.findViewById(R.id.tvTopLine);
            holder.tvDot = convertView.findViewById(R.id.tvDot);
            holder.tv_new = convertView.findViewById(R.id.tv_new);
            holder.ivGoods = convertView.findViewById(R.id.iv_goods);
            holder.ivGoodsTwo = convertView.findViewById(R.id.iv_goods_two);
            convertView.setTag(holder);
        }

        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            holder.tvTopLine.setVisibility(View.INVISIBLE);
            holder.tv_new.setVisibility(View.VISIBLE);
            // 字体颜色加深
            holder.tvAcceptTime.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvAcceptStation.setTextColor(context.getResources().getColor(R.color.black));
            holder.ivGoods.setVisibility(View.VISIBLE);
            holder.ivGoodsTwo.setVisibility(View.VISIBLE);
            holder.ivGoods.setImageResource(R.drawable.home_middle_five);

            holder.tvDot.setBackgroundResource(R.mipmap.timelline_dot_secord);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            holder.tvTopLine.setVisibility(View.VISIBLE);
            holder.tv_new.setVisibility(View.INVISIBLE);

            holder.tvAcceptTime.setTextColor(context.getResources().getColor(R.color.alpha_40_black));
            holder.tvAcceptStation.setTextColor(context.getResources().getColor(R.color.alpha_40_black));
            holder.ivGoods.setVisibility(View.GONE);
            holder.ivGoodsTwo.setVisibility(View.GONE);
            holder.tvDot.setBackgroundResource(R.mipmap.timelline_dot_first);
        }
        holder.tvAcceptTime.setText(traceBean.getAcceptTime());
        SpannableStringUtils sp = new SpannableStringUtils(traceBean.getAcceptStation());
        checkPhoneText(holder.tvAcceptStation, sp, traceBean.getAcceptStation());
        return convertView;
    }
    @Override
    public int getItemViewType(int id) {
        if (id == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    static class ViewHolder {
        public TextView tvAcceptTime, tvAcceptStation;
        public TextView tvTopLine, tvDot, tv_new;
        public ImageView ivGoods, ivGoodsTwo;
    }

    // 正则表达式，提取我们所有匹配的内容；
    private void checkPhoneText(TextView tvAcceptStation, SpannableStringUtils sp, String text) {
        Pattern pattern = Pattern
                .compile("\\d{3}-\\d{8}|\\d{4}-\\d{7}|\\d{11}");
        final Matcher matcher = pattern.matcher(text);
        int start = 0;
        //遍历取出字符串中所有的符合条件的；
        while (matcher.find(start)) {
            start = matcher.end();
            sp.setColor(Color.BLUE, matcher.start(), matcher.end())
                    .setBackGround(ContextCompat.getColor(context,R.color.white), matcher.start(), matcher.end())
                    .setBold( matcher.start(), matcher.end())
                    .setOnClick(matcher.start(), matcher.end(), ContextCompat.getColor(context,R.color.blue))

                    .setOnClickSpanListener(new SpannableStringUtils.OnClickSpanListener() {
                        @Override
                        public void OnClickSpanListener() {
                            showDialog(Gravity.BOTTOM, R.style.Bottom_Top_aniamtion,matcher.group());
                        }
                    });


            if (start >= text.length()) {
                break;
            }
        }
        tvAcceptStation.setMovementMethod(LinkMovementMethod.getInstance());
        tvAcceptStation.setTextSize(12);
        tvAcceptStation.setText(sp);

    }


    private void showDialog(int grary, int animationStyle, final String group) {
        BaseDialog.Builder builder = new BaseDialog.Builder(context);
        dialog = builder.setViewId(R.layout.phone_dialog)
                .setPaddingdp(10, 0, 10, 0)
                .setGravity(grary)
                .setAnimation(animationStyle)
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .isOnTouchCanceled(true)
                .addViewOnClickListener(R.id.bt_callPhone, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + group);
                        intent.setData(data);
                        context.startActivity(intent);
                        dialog.close();
                    }
                })
                .addViewOnClickListener(R.id.bt_dissmess, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.close();
                    }
                })
                .builder();
        dialog.show();
        Button phone= dialog.getView(R.id.bt_callPhone);
        phone.setText(group);

    }




}
