package bwie.com.myshop.utils;

/**
 * Created by 13435 on 2017/10/11.
 */

public class OptionUtil{

    public static final String IP="http://169.254.131.247/mobile/index.php?act=";//主体
    public static final String CLIENT="android";                                  //client常量
    public static final String LOGIN=IP+"login";                                  //登录
    public static final String REGISTER=IP+"login&op=register";                  //注册
    public static final String LOGOUT=IP+"logout";                                //注销
    public static final String STAIR=IP+"goods_class";
    public static final String SECOND=IP+"goods_class&gc_id=";
    public static final String GOODS_FIND=IP+"goods&op=goods_list&page=";
    public static final String BLURB=IP+"goods&op=goods_detail";
    public static final String INTRODUCE = IP+"goods&op=goods_body";
    public static final String PLUS = IP+"member_cart&op=cart_add";
    public static final String SHOPCAR = IP+"member_cart&op=cart_list";
    public static final String DEL = IP+"member_cart&op=cart_del";
    public static final String ADD_ADDRESS = IP+"member_address&op=address_add";
    public static final String SHOWADDRESS = IP+"member_address&op=address_list";
    public static final String ADDRESS_DEL = IP+"member_address&op=address_del";
}
