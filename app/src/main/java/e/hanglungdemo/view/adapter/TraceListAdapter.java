package e.hanglungdemo.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import e.hanglungdemo.MainActivity;
import e.hanglungdemo.R;
import e.hanglungdemo.view.bean.TraceBean;
import e.library.T;

public class TraceListAdapter extends BaseAdapter {
    private Context context;
    private List<TraceBean> traceBeanList;
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL = 0x0001;
    private Intent intent;

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
        //holder.tvAcceptStation.setText(traceBean.getAcceptStation());
        SpannableString sp = new SpannableString(traceBean.getAcceptStation());

        checkPhoneText(holder.tvAcceptStation, sp, traceBean.getAcceptStation());
        OnClickListener(holder.tvAcceptStation);
        return convertView;
    }

    private void OnClickListener(TextView tvAcceptStation) {
        tvAcceptStation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean ret = false;
                CharSequence text = ((TextView) v).getText();
                Spannable stext = Spannable.Factory.getInstance().newSpannable(text);
                TextView widget = (TextView) v;
                int action = event.getAction();
                //根据点击判断是否在spannable对象上；
                if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {

                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    x -= widget.getTotalPaddingLeft();
                    y -= widget.getTotalPaddingTop();

                    x += widget.getScrollX();
                    y += widget.getScrollY();

                    Layout layout = widget.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);

                    ClickableSpan[] link = stext.getSpans(off, off, ClickableSpan.class);

                    if (link.length != 0) {
                        if (action == MotionEvent.ACTION_UP) {
                            link[0].onClick(widget);
                        }
                        ret = true;
                    }
                }
                return ret;
            }
        });
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
    private void checkPhoneText(TextView tvAcceptStation, SpannableString sp, String text) {
        Pattern pattern = Pattern
                .compile("\\d{3}-\\d{8}|\\d{4}-\\d{7}|\\d{11}");
        final Matcher matcher = pattern.matcher(text);

        int start = 0;
        //遍历取出字符串中所有的符合条件的；
        while (matcher.find(start)) {
            start = matcher.end();


//            sp.setSpan(new MyUrlSpan(matcher.group()), matcher.start(),
//                    matcher.end(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + matcher.group());
                    intent.setData(data);
                    context.startActivity(intent);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                    super.updateDrawState(ds);
                }
            };
            //设置点击
            sp.setSpan(clickableSpan, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (start >= text.length()) {
                break;
            }
        }
        tvAcceptStation.setText(sp);

    }

//    class MyUrlSpan extends ClickableSpan {
//        private String mUrl;
//        private Intent intent;
//
//        MyUrlSpan(String url) {
//            mUrl = url;
//        }
//        //点击链接下划线，弹出dialog，提示提电话，或者发短信；
//        @Override
//        public void onClick(View widget) {
////            intent = new Intent(
////                    Intent.ACTION_CALL, Uri
////                    .parse("tel:" + mUrl));
////            context.startActivity(intent);
//            intent = new Intent(Intent.ACTION_CALL);
//            Uri data = Uri.parse("tel:" + mUrl);
//            intent.setData(data);
//            context.startActivity(intent);
//
//        }
//    }


}
