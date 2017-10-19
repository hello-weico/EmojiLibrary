package com.autohome.emojilibrary.manager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;


import com.autohome.emojilibrary.emoji.EmojiconSpan;
import com.autohome.emojilibrary.R;
import com.autohome.emojilibrary.emoji.Emojicon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 表情管理工具<br>
 * <p>在静态模块中加载所有表情，按显示页生成EmojiIcon集合，</p>
 */
public class ExpressionUtil {

    /**随着配置数量变化*/
    private static int ptExpressNum = 90;// 普通表情总数。

//    private static Map<String, String> reviceExpressMap = new HashMap<String, String>();
    /**Key为表情关键字，value为drawable id*/
    private static Map<String, Integer> mEmojiMap = new HashMap();
//    private static Map.Entry<String, String> [] receiveEntries;
    /**表情关键字和对应的drawable id组合的entry数组*/
    private static Map.Entry<String, Integer> [] mEmojiEntries;
//    private static Resources res;
//    private static Drawable drawable;

    private static final int DRAWABLE_CACHE_SIZE = 50;
    private static LruCache<Integer, Drawable> drawableCaches = new LruCache(DRAWABLE_CACHE_SIZE);


    static {
        mEmojiMap.put("[微笑]", R.drawable.smiley_0);
        mEmojiMap.put("[撇嘴]", R.drawable.smiley_1);
        mEmojiMap.put("[色]", R.drawable.smiley_2);
        mEmojiMap.put("[发呆]", R.drawable.smiley_3);
        mEmojiMap.put("[得意]", R.drawable.smiley_4);
        mEmojiMap.put("[流泪]", R.drawable.smiley_5);
        mEmojiMap.put("[害羞]", R.drawable.smiley_6);
        mEmojiMap.put("[闭嘴]", R.drawable.smiley_7);
        mEmojiMap.put("[睡]", R.drawable.smiley_8);
        mEmojiMap.put("[大哭]", R.drawable.smiley_9);
        mEmojiMap.put("[尴尬]", R.drawable.smiley_10);
        mEmojiMap.put("[发怒]", R.drawable.smiley_11);
        mEmojiMap.put("[调皮]", R.drawable.smiley_12);
        mEmojiMap.put("[嘻嘻]", R.drawable.smiley_13);
        mEmojiMap.put("[惊讶]", R.drawable.smiley_14);
        mEmojiMap.put("[难过]", R.drawable.smiley_15);
        mEmojiMap.put("[酷]", R.drawable.smiley_16);
        mEmojiMap.put("[冷汗]", R.drawable.smiley_17);
        mEmojiMap.put("[抓狂]", R.drawable.smiley_18);
        mEmojiMap.put("[吐]", R.drawable.smiley_19);
        mEmojiMap.put("[偷笑]", R.drawable.smiley_20);
        mEmojiMap.put("[可爱]", R.drawable.smiley_21);
        mEmojiMap.put("[白眼]", R.drawable.smiley_22);
        mEmojiMap.put("[傲慢]", R.drawable.smiley_23);
        mEmojiMap.put("[饥饿]", R.drawable.smiley_24);
        mEmojiMap.put("[困]", R.drawable.smiley_25);
        mEmojiMap.put("[惊恐]", R.drawable.smiley_26);
        mEmojiMap.put("[汗]", R.drawable.smiley_27);
        mEmojiMap.put("[憨笑]", R.drawable.smiley_28);
        mEmojiMap.put("[大兵]", R.drawable.smiley_29);
        mEmojiMap.put("[奋斗]", R.drawable.smiley_30);
        mEmojiMap.put("[咒骂]", R.drawable.smiley_31);
        mEmojiMap.put("[疑问]", R.drawable.smiley_32);
        mEmojiMap.put("[嘘]", R.drawable.smiley_33);
        mEmojiMap.put("[晕]", R.drawable.smiley_34);
        mEmojiMap.put("[折磨]", R.drawable.smiley_35);
        mEmojiMap.put("[衰]", R.drawable.smiley_36);
        mEmojiMap.put("[骷髅]", R.drawable.smiley_37);
        mEmojiMap.put("[敲打]", R.drawable.smiley_38);
        mEmojiMap.put("[再见]", R.drawable.smiley_39);
        mEmojiMap.put("[擦汗]", R.drawable.smiley_40);
        mEmojiMap.put("[挖鼻]", R.drawable.smiley_41);
        mEmojiMap.put("[鼓掌]", R.drawable.smiley_42);
        mEmojiMap.put("[糗大了]", R.drawable.smiley_43);
        mEmojiMap.put("[坏笑]", R.drawable.smiley_44);
        mEmojiMap.put("[左哼哼]", R.drawable.smiley_45);
        mEmojiMap.put("[右哼哼]", R.drawable.smiley_46);
        mEmojiMap.put("[哈欠]", R.drawable.smiley_47);
        mEmojiMap.put("[鄙视]", R.drawable.smiley_48);
        mEmojiMap.put("[委屈]", R.drawable.smiley_49);
        mEmojiMap.put("[快哭了]", R.drawable.smiley_50);
        mEmojiMap.put("[阴险]", R.drawable.smiley_51);
        mEmojiMap.put("[亲亲]", R.drawable.smiley_52);
        mEmojiMap.put("[吓]", R.drawable.smiley_53);
        mEmojiMap.put("[可怜]", R.drawable.smiley_54);
        mEmojiMap.put("[菜刀]", R.drawable.smiley_55);
        mEmojiMap.put("[西瓜]", R.drawable.smiley_56);
        mEmojiMap.put("[啤酒]", R.drawable.smiley_57);
        mEmojiMap.put("[篮球]", R.drawable.smiley_58);
        mEmojiMap.put("[乒乓]", R.drawable.smiley_59);
        mEmojiMap.put("[咖啡]", R.drawable.smiley_60);
        mEmojiMap.put("[饭]", R.drawable.smiley_61);
        mEmojiMap.put("[猪头]", R.drawable.smiley_62);
        mEmojiMap.put("[玫瑰]", R.drawable.smiley_63);
        mEmojiMap.put("[凋零]", R.drawable.smiley_64);
        mEmojiMap.put("[示爱]", R.drawable.smiley_65);
        mEmojiMap.put("[爱心]", R.drawable.smiley_66);
        mEmojiMap.put("[心碎]", R.drawable.smiley_67);
        mEmojiMap.put("[蛋糕]", R.drawable.smiley_68);
        mEmojiMap.put("[闪电]", R.drawable.smiley_69);
        mEmojiMap.put("[炸弹]", R.drawable.smiley_70);
        mEmojiMap.put("[刀]", R.drawable.smiley_71);
        mEmojiMap.put("[足球]", R.drawable.smiley_72);
        mEmojiMap.put("[瓢虫]", R.drawable.smiley_73);
        mEmojiMap.put("[便便]", R.drawable.smiley_74);
        mEmojiMap.put("[月亮]", R.drawable.smiley_75);
        mEmojiMap.put("[太阳]", R.drawable.smiley_76);
        mEmojiMap.put("[礼物]", R.drawable.smiley_77);
        mEmojiMap.put("[拥抱]", R.drawable.smiley_78);
        mEmojiMap.put("[强]", R.drawable.smiley_79);
        mEmojiMap.put("[弱]", R.drawable.smiley_80);
        mEmojiMap.put("[握手]", R.drawable.smiley_81);
        mEmojiMap.put("[胜利]", R.drawable.smiley_82);
        mEmojiMap.put("[抱拳]", R.drawable.smiley_83);
        mEmojiMap.put("[勾引]", R.drawable.smiley_84);
        mEmojiMap.put("[拳头]", R.drawable.smiley_85);
        mEmojiMap.put("[差劲]", R.drawable.smiley_86);
        mEmojiMap.put("[爱你]", R.drawable.smiley_87);
        mEmojiMap.put("[NO]", R.drawable.smiley_88);
        mEmojiMap.put("[OK]", R.drawable.smiley_89);
        Set<Map.Entry<String, Integer>> entrySet= mEmojiMap.entrySet();
        mEmojiEntries = new HashMap.Entry[entrySet.size()];
        entrySet.toArray(mEmojiEntries);

        ptExpressNum = mEmojiMap.size();

//        reviceExpressMap.put("[微笑]", "smiley_0");
//        reviceExpressMap.put("[撇嘴]", "smiley_1");
//        reviceExpressMap.put("[色]", "smiley_2");
//        reviceExpressMap.put("[发呆]", "smiley_3");
//        reviceExpressMap.put("[得意]", "smiley_4");
//        reviceExpressMap.put("[流泪]", "smiley_5");
//        reviceExpressMap.put("[害羞]", "smiley_6");
//        reviceExpressMap.put("[闭嘴]", "smiley_7");
//        reviceExpressMap.put("[睡]", "smiley_8");
//        reviceExpressMap.put("[大哭]", "smiley_9");
//        reviceExpressMap.put("[尴尬]", "smiley_10");
//        reviceExpressMap.put("[发怒]", "smiley_11");
//        reviceExpressMap.put("[调皮]", "smiley_12");
//        reviceExpressMap.put("[嘻嘻]", "smiley_13");
//        reviceExpressMap.put("[惊讶]", "smiley_14");
//        reviceExpressMap.put("[难过]", "smiley_15");
//        reviceExpressMap.put("[酷]", "smiley_16");
//        reviceExpressMap.put("[冷汗]", "smiley_17");
//        reviceExpressMap.put("[抓狂]", "smiley_18");
//        reviceExpressMap.put("[吐]", "smiley_19");
//        reviceExpressMap.put("[偷笑]", "smiley_20");
//        reviceExpressMap.put("[可爱]", "smiley_21");
//        reviceExpressMap.put("[白眼]", "smiley_22");
//        reviceExpressMap.put("[傲慢]", "smiley_23");
//        reviceExpressMap.put("[饥饿]", "smiley_24");
//        reviceExpressMap.put("[困]", "smiley_25");
//        reviceExpressMap.put("[惊恐]", "smiley_26");
//        reviceExpressMap.put("[汗]", "smiley_27");
//        reviceExpressMap.put("[憨笑]", "smiley_28");
//        reviceExpressMap.put("[大兵]", "smiley_29");
//        reviceExpressMap.put("[奋斗]", "smiley_30");
//        reviceExpressMap.put("[咒骂]", "smiley_31");
//        reviceExpressMap.put("[疑问]", "smiley_32");
//        reviceExpressMap.put("[嘘]", "smiley_33");
//        reviceExpressMap.put("[晕]", "smiley_34");
//        reviceExpressMap.put("[折磨]", "smiley_35");
//        reviceExpressMap.put("[衰]", "smiley_36");
//        reviceExpressMap.put("[骷髅]", "smiley_37");
//        reviceExpressMap.put("[敲打]", "smiley_38");
//        reviceExpressMap.put("[再见]", "smiley_39");
//        reviceExpressMap.put("[擦汗]", "smiley_40");
//        reviceExpressMap.put("[挖鼻]", "smiley_41");
//        reviceExpressMap.put("[鼓掌]", "smiley_42");
//        reviceExpressMap.put("[糗大了]", "smiley_43");
//        reviceExpressMap.put("[坏笑]", "smiley_44");
//        reviceExpressMap.put("[左哼哼]", "smiley_45");
//        reviceExpressMap.put("[右哼哼]", "smiley_46");
//        reviceExpressMap.put("[哈欠]", "smiley_47");
//        reviceExpressMap.put("[鄙视]", "smiley_48");
//        reviceExpressMap.put("[委屈]", "smiley_49");
//        reviceExpressMap.put("[快哭了]", "smiley_50");
//        reviceExpressMap.put("[阴险]", "smiley_51");
//        reviceExpressMap.put("[亲亲]", "smiley_52");
//        reviceExpressMap.put("[吓]", "smiley_53");
//        reviceExpressMap.put("[可怜]", "smiley_54");
//        reviceExpressMap.put("[菜刀]", "smiley_55");
//        reviceExpressMap.put("[西瓜]", "smiley_56");
//        reviceExpressMap.put("[啤酒]", "smiley_57");
//        reviceExpressMap.put("[篮球]", "smiley_58");
//        reviceExpressMap.put("[乒乓]", "smiley_59");
//        reviceExpressMap.put("[咖啡]", "smiley_60");
//        reviceExpressMap.put("[饭]", "smiley_61");
//        reviceExpressMap.put("[猪头]", "smiley_62");
//        reviceExpressMap.put("[玫瑰]", "smiley_63");
//        reviceExpressMap.put("[凋零]", "smiley_64");
//        reviceExpressMap.put("[示爱]", "smiley_65");
//        reviceExpressMap.put("[爱心]", "smiley_66");
//        reviceExpressMap.put("[心碎]", "smiley_67");
//        reviceExpressMap.put("[蛋糕]", "smiley_68");
//        reviceExpressMap.put("[闪电]", "smiley_69");
//        reviceExpressMap.put("[炸弹]", "smiley_70");
//        reviceExpressMap.put("[刀]", "smiley_71");
//        reviceExpressMap.put("[足球]", "smiley_72");
//        reviceExpressMap.put("[瓢虫]", "smiley_73");
//        reviceExpressMap.put("[便便]", "smiley_74");
//        reviceExpressMap.put("[月亮]", "smiley_75");
//        reviceExpressMap.put("[太阳]", "smiley_76");
//        reviceExpressMap.put("[礼物]", "smiley_77");
//        reviceExpressMap.put("[拥抱]", "smiley_78");
//        reviceExpressMap.put("[强]", "smiley_79");
//        reviceExpressMap.put("[弱]", "smiley_80");
//        reviceExpressMap.put("[握手]", "smiley_81");
//        reviceExpressMap.put("[胜利]", "smiley_82");
//        reviceExpressMap.put("[抱拳]", "smiley_83");
//        reviceExpressMap.put("[勾引]", "smiley_84");
//        reviceExpressMap.put("[拳头]", "smiley_85");
//        reviceExpressMap.put("[差劲]", "smiley_86");
//        reviceExpressMap.put("[爱你]", "smiley_87");
//        reviceExpressMap.put("[NO]", "smiley_88");
//        reviceExpressMap.put("[OK]", "smiley_89");
////		reviceExpressMap.put("[两颗心]", "smiley_90");
////		reviceExpressMap.put("[冷饮]", "smiley_91");
////		reviceExpressMap.put("[剪刀头]", "smiley_92");
////		reviceExpressMap.put("[小鬼]", "smiley_93");
////		reviceExpressMap.put("[小黄鸭]", "smiley_94");
////		reviceExpressMap.put("[恶魔]", "smiley_95");
////		reviceExpressMap.put("[星星]", "smiley_96");
////		reviceExpressMap.put("[小蛋糕]", "smiley_97");
////		reviceExpressMap.put("[大足球]", "smiley_98");
////		reviceExpressMap.put("[高跟鞋]", "smiley_99");
//        Set<Map.Entry<String, String>> entrySet= reviceExpressMap.entrySet();
//        receiveEntries = new HashMap.Entry[entrySet.size()];
//        entrySet.toArray(receiveEntries);
    }


    /**
     * 处理字符串中的表情，得到spanned对象。
     * @param context
     * @param str 需要添加span的文本
     * @param zhengze 匹配表情关键字的正则表达式
     * @param faceWidth 表情显示区域，drawable bounds
     * @return
     * @Description: 得到一个SpannableStringBuilder对象，通过传入的字符串,并进行正则判断
     */
    public static SpannableStringBuilder getExpressionString(Context context, String str, String zhengze, int faceWidth) {
        if (TextUtils.isEmpty(str)) {
            return new SpannableStringBuilder();
        }

        SpannableStringBuilder spannableString = new SpannableStringBuilder(str);
        Pattern emojiPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE); // 通过传入的正则表达式来生成一个pattern
        dealExpression(context, spannableString, emojiPatten, 0, DynamicDrawableSpan.ALIGN_BASELINE, faceWidth, faceWidth);
        return spannableString;
    }

    /**
     * 对spanableString进行正则判断，如果符合要求，则以表情图片代替
     * @param context
     * @param spannableString
     * @param patten 正则
     * @param start
     * @param emojiAlignment 对齐方式
     * @param faceWidth  表情图片的宽度,单位px
     * @param textSize 字体大小
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws NumberFormatException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws StackOverflowError
     * @Description: 对spanableString进行正则判断，如果符合要求，则以表情图片代替
     * @author hufeng
     * @date 2013-11-19
     *
     * @see #getExpressionString(Context, String, String, int)
     */
    public static void dealExpression(Context context, SpannableStringBuilder spannableString, Pattern patten, int start,int emojiAlignment,
                                      int faceWidth, int textSize) {

//        if (res == null) {
//            res = context.getResources();
//        }

        Log.i("dl", "dealExpression() faceWidth = " + faceWidth);
        try {
//        Drawable drawable = null;
            //先删除全部EmojiconSpan
            EmojiconSpan[] spans = spannableString.getSpans(0, spannableString.length(), EmojiconSpan.class);
            if (spans!=null&&spans.length>0) {
                for (EmojiconSpan span:spans)
                spannableString.removeSpan(span);
            }

            Matcher matcher = patten.matcher(spannableString);
            while (matcher.find()) {
                String key = matcher.group();
                int keyStart = matcher.start();
                // 计算该图片名字的长度，也就是要替换的字符串的长度
                int keyEnd = matcher.end();

                if (keyStart < start) {
                    continue;
                }
//            String name = reviceExpressMap.get(key);
                if (TextUtils.isEmpty(key)) {
                    continue;
                }

                int resId = mEmojiMap.get(key).intValue();
                if (resId != 0) {
//                drawable = getDrawableFromCache(resId);
//                if (drawable != null && faceWidth > 0) {
//                    drawable.setBounds(0, 0, faceWidth, faceWidth);
//                }
                    EmojiconSpan imageSpan = new EmojiconSpan(context, resId, faceWidth, emojiAlignment, textSize);
                    spannableString.setSpan(imageSpan, keyStart, keyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 将该图片替换字符串中规定的位置中

//				if (matcher.end() < spannableString.length()) { // 如果整个字符串还未验证完，则继续。。
//					dealExpression(context, spannableString, patten, matcher.end(), faceWidth);
//				}
//				break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error error){
            error.printStackTrace();
        }
    }

    public static void addEmojis(Context context, SpannableStringBuilder spannableString, int emojiSize, int emojiAlignment, int textSize) {
        String zhengze = "\\[[a-z|A-Z|\u4e00-\u9fa5]{1,3}\\]"; // 正则表达式，用来判断消息内是否有表情
        Pattern emojiPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE); // 通过传入的正则表达式来生成一个pattern
        dealExpression(context, spannableString, emojiPatten, 0, emojiAlignment, emojiSize, textSize);
    }

//    /**需要确保res不为null*/
//    private static Drawable getDrawableFromCache(int key) {
//        Drawable drawable = drawableCaches.get(key);
//        if (drawable == null && res != null) {
//            drawable = res.getDrawable(key);
//            drawableCaches.put(key, drawable);
//        }
//        return drawable;
//    }

    /**
     * @param c
     * @param text
     * @return
     * @Description: 解析字符串中的表情标签返回SpannableString累心
     */
//    public static SpannableStringBuilder textToSpannableStr(Context c, String text, int faceWidth) {
//        String zhengze = "\\[[a-z|A-Z|\u4e00-\u9fa5]{1,3}\\]"; // 正则表达式，用来判断消息内是否有表情
//        // String zhengze = "icon_expression[0-9]{1,3}"; // 正则表达式，用来判断消息内是否有表情
//        SpannableStringBuilder spannableString = new SpannableStringBuilder("");
//        try {
//            spannableString = ExpressionUtil.getExpressionString(c, text, zhengze, faceWidth);
//            return spannableString;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return spannableString;
//        }
//    }


    /**
     * 获取某一页的表情数据
     * @param pageNum 表情页码，从0开始
     * @param num 每页表情个数
     * @return
     */
    public static ArrayList<Emojicon> getEmojiiconList(int pageNum, int num) {
        int start = pageNum * num;
        //下一页的开始
        int end = (pageNum+1) * num;

        if (ptExpressNum < start) {
            start = ptExpressNum;
        }
        if (ptExpressNum < end) {
            end = ptExpressNum;
        }

        ArrayList<Emojicon> emojicons = new ArrayList(end-start);
        try {
            for (int i = start; i < end; i++) {
                Map.Entry<String, Integer> entry = mEmojiEntries[i];
//                String name = entry.getValue();
//                Field field = R.drawable.class.getDeclaredField(name);
//                int id = field.getInt(name);
                emojicons.add(new Emojicon(entry.getValue().intValue(), entry.getKey()));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }

        //第页最后一个表情为删除按钮
        if (emojicons != null && emojicons.size() > 0) {
            emojicons.add(new Emojicon(R.drawable.icon_del_express, "[backspace]"));
        }
        return emojicons;
    }

    /**获取表情页数*/
    public static int getExpressPageCount(int pageNum) {
        return  (ptExpressNum / pageNum + (ptExpressNum % pageNum == 0 ? 0 : 1));
    }

//    public static int getEmojiIconIdByName(String emojiName){
//        String iconName=reviceExpressMap.get(emojiName);
//    }

    /**输入到表情到编辑框*/
    public static void input(EditText editText, Emojicon emojicon) {
        if (editText == null || emojicon == null) {
            return;
        }

        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if (start < 0) {
            editText.append(emojicon.getEmoji());
        } else {
            editText.getText().replace(Math.min(start, end), Math.max(start, end), emojicon.getEmoji(), 0, emojicon.getEmoji().length());
        }
//        Context context = editText.getContext();
//        Spanned spanned = ExpressionUtil.textToSpannableStr(context, editText.getText().toString(), ScreenUtils.dpToPx(context, 20));
//        editText.setText(spanned);
//        editText.setSelection(start + emojicon.getEmoji().length());
    }

    /**表情面板中删除功能*/
    public static void backspace(EditText editText) {
        KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
        editText.dispatchKeyEvent(event);
    }



}
