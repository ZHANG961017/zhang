package bwie.com.myshop.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bwie.com.myshop.R;
import bwie.com.myshop.activity.BaseActivity;
import bwie.com.myshop.bean.DelBean;
import bwie.com.myshop.bean.ShopCarBean;
import bwie.com.myshop.utils.OptionUtil;
import bwie.com.myshop.utils.okhttp.GsonObjectCallback;
import bwie.com.myshop.utils.okhttp.OkHttp3Utils;
import okhttp3.Call;

import static android.R.id.list;

/**
 * Created by 13435 on 2017/10/20.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter{

    private Context ctx;
    private List<ShopCarBean.DatasBean.CartListBean> group;
    private CheckBox selectAll;
    private TextView priceAll;
    private TextView countAll;

    public ExpandableAdapter(Context ctx, List<ShopCarBean.DatasBean.CartListBean> group,CheckBox selectAll,TextView priceAll,TextView countAll) {
        this.ctx = ctx;
        this.group = group;
        this.selectAll = selectAll;
        this.priceAll = priceAll;
        this.countAll = countAll;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return group.get(groupPosition).getGoods().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return group.get(groupPosition).getGoods().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if(convertView == null){
            groupHolder = new GroupHolder();
            convertView = View.inflate(ctx, R.layout.group, null);
            groupHolder.check_group = convertView.findViewById(R.id.check_group);
            groupHolder.shopsName = convertView.findViewById(R.id.shopsName);
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (GroupHolder) convertView.getTag();
        }
        //groupHolder.check_group.setOnClickListener(new OnGroupClickListener(groupPosition, groupHolder.check_group));
        groupHolder.shopsName.setText(group.get(groupPosition).getStore_name());
        groupHolder.check_group.setChecked(group.get(groupPosition).isGroupCheck());
        groupHolder.check_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(group.get(groupPosition).isGroupCheck()){
                    group.get(groupPosition).setGroupCheck(false);
                    selectAll.setChecked(false);
                    for(int i=0;i<group.get(groupPosition).getGoods().size();i++){
                        group.get(groupPosition).getGoods().get(i).setChildCheck(false);
                    }
                }else{
                    int GroupSum=0;
                    group.get(groupPosition).setGroupCheck(true);
                    for(int i=0;i<group.get(groupPosition).getGoods().size();i++){
                        group.get(groupPosition).getGoods().get(i).setChildCheck(true);
                    }
                    for(int j=0;j<group.size();j++){
                        if(group.get(j).isGroupCheck()){
                            GroupSum++;
                        }
                    }
                    if( GroupSum == group.size()){
                        selectAll.setChecked(true);
                    }
                }
                MoneyAllAndCountAll();
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if(convertView == null){
            childHolder = new ChildHolder();
            convertView = View.inflate(ctx, R.layout.child, null);
            childHolder.check_child = convertView.findViewById(R.id.check_child);
            childHolder.iv_goodsImage = convertView.findViewById(R.id.iv_goodsImage);
            childHolder.tv_goodsName = convertView.findViewById(R.id.tv_goodsName);
            childHolder.tv_goodsPrice = convertView.findViewById(R.id.tv_goodsPrice);
            childHolder.goodsAmount = convertView.findViewById(R.id.goodsAmount);
            childHolder.del = convertView.findViewById(R.id.del);
            convertView.setTag(childHolder);
        }else{
            childHolder = (ChildHolder) convertView.getTag();
        }
        //childHolder.check_child.setOnClickListener(new OnChildCheckListener(groupPosition,childPosition,childHolder.check_child));
        Picasso.with(ctx).load(group.get(groupPosition).getGoods().get(childPosition).getGoods_image_url()).into(childHolder.iv_goodsImage);
        childHolder.tv_goodsName.setText(group.get(groupPosition).getGoods().get(childPosition).getGoods_name());
        childHolder.tv_goodsPrice.setText(group.get(groupPosition).getGoods().get(childPosition).getGoods_price());
        childHolder.goodsAmount.setText(group.get(groupPosition).getGoods().get(childPosition).getGoods_num());
        childHolder.check_child.setChecked(group.get(groupPosition).getGoods().get(childPosition).isChildCheck());
        childHolder.check_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(group.get(groupPosition).getGoods().get(childPosition).isChildCheck()){
                    group.get(groupPosition).getGoods().get(childPosition).setChildCheck(false);
                    selectAll.setChecked(false);
                    group.get(groupPosition).setGroupCheck(false);
                }else{
                    int childSum=0;
                    int groupSum=0;
                    group.get(groupPosition).getGoods().get(childPosition).setChildCheck(true);
                    for(int i=0;i<group.get(groupPosition).getGoods().size();i++){
                        if(group.get(groupPosition).getGoods().get(i).isChildCheck()){
                            childSum++;
                        }
                    }
                    if(group.get(groupPosition).getGoods().size() == childSum){
                        group.get(groupPosition).setGroupCheck(true);
                    }
                    for(int j=0;j<group.size();j++){
                        if(group.get(groupPosition).isGroupCheck()){
                            groupSum++;
                        }
                    }
                    if(group.size() == groupSum){
                        selectAll.setChecked(true);
                    }
                }
                MoneyAllAndCountAll();
                notifyDataSetChanged();
                Toast.makeText(ctx, "点击了:"+childPosition, Toast.LENGTH_SHORT).show();
            }
        });
        childHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences instance = BaseActivity.getSharedPreferencesInstance(ctx);
                String key = instance.getString("key", "");
                Map<String,String> map = new HashMap<String, String>();
                map.put("key",key);
                map.put("cart_id",group.get(groupPosition).getGoods().get(childPosition).getCart_id());
                OkHttp3Utils.doPost(OptionUtil.DEL, map, new GsonObjectCallback<DelBean>() {
                    @Override
                    public void onUi(DelBean delBean) {
                        if(delBean.getCode() == 200){
                            Toast.makeText(ctx, "...", Toast.LENGTH_SHORT).show();
                            group.get(groupPosition).getGoods().remove(childPosition);
                            notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailed(Call call, IOException e) {
                    }
                });
            }
        });
        notifyDataSetChanged();
        return convertView;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
    @Override
    public void onGroupExpanded(int groupPosition) {

    }
    @Override
    public void onGroupCollapsed(int groupPosition) {

    }
    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }
    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    class GroupHolder{
        CheckBox check_group;
        TextView shopsName;
    }
    class ChildHolder{
        CheckBox check_child;
        ImageView iv_goodsImage;
        TextView tv_goodsName;
        TextView tv_goodsPrice;
        TextView goodsAmount;
        Button del;
    }

    public void MoneyAllAndCountAll(){
        int sum=0;
        double money = 0;
        for (ShopCarBean.DatasBean.CartListBean cartListBean:group) {
            for(ShopCarBean.DatasBean.CartListBean.GoodsBean goodsBean:cartListBean.getGoods()){
                if(goodsBean.isChildCheck()){
                    sum+=Integer.parseInt(goodsBean.getGoods_num());
                    double aDouble = Double.parseDouble(goodsBean.getGoods_total());
                    money+=aDouble;
                }
            }
        }
        countAll.setText(sum+"");
        priceAll.setText(money+"");
    }
}
