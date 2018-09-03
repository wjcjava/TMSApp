package e.hanglungdemo.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import e.hanglungdemo.R;
import e.hanglungdemo.view.bean.TraceBean;

public class TraceListAdapter extends BaseAdapter {
    private Context context;
    private List<TraceBean> traceBeanList ;
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL = 0x0001;

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
            holder.tvAcceptStation =  convertView.findViewById(R.id.tvAcceptStation);
            holder.tvTopLine =  convertView.findViewById(R.id.tvTopLine);
            holder.tvDot =  convertView.findViewById(R.id.tvDot);
            holder.tv_new = convertView.findViewById(R.id.tv_new);
            holder.ivGoods=convertView.findViewById(R.id.iv_goods);
            holder.ivGoodsTwo=convertView.findViewById(R.id.iv_goods_two);
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
        holder.tvAcceptStation.setText(traceBean.getAcceptStation());
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
        public ImageView ivGoods,ivGoodsTwo;
    }

}
