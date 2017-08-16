# RecycleItemOrition
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency

dependencies {
	        compile 'com.github.Rainorsad:RecycleItemOrition:v2.0'
	}
	
注意：传递的参数都是dp和px，内部已经做了处理

使用方法：
ItemOrition itemOrition = new ItemOrition(Context context);
默认是VERTICAL，可以设置HORIZONTAL。

1.设置分隔线颜色
itemOrition.setColor(int color)；

2.设置分隔线高度
itemOrition.setHeight(int height);

3.设置分隔线padding值（只设置一个值则左右都为这个值，也可以设置两个值）
itemOrition.setPadding(int one);
itemOrition.setPadding(int one,int two);

4.设置文字类型的标签
itemOrition.setLabel(boolean islabel, String text, int padright)（islabel TRUE，text 标签文案，padright 标签距右边距）
itemOrition.setLabel(boolean islabel, String text, int padright, int color, int textsize)(islabel TRUE，text 标签文案，padright 标签距右边距，color 标签文字颜色，textsize 标签字体大小)

5.设置图片类型的标签属性
itemOrition.setImageLabel(boolean isimglabel, int imgResource)(isimglabel true,imgResource 图片资源)

6.仿通讯录分组
/**
     *
     * @param context
     * @param orientation 水平还是垂直
     * @param backgroundcolor  主题栏背景色
     * @param titlecolor 标题颜色
     * @param titleTextSize 标题大小
     * @param titleTextLeftPadding 标题距左边距离
     * @param callback 内置回掉借口，传入数据的第一个字母的大写
 */
 recyclerView.addItemDecoration(new ItemOrition(Context context, int orientation, int backgroundcolor,int titlecolor,int titleTextSize,int titleTextLeftPadding, new ItemOrition.DecorationCallback() {
            @Override
            public long getGroupId(int position) {
                return Character.toUpperCase(s.get(position).charAt(0));
            }

            @Override
            public String getGroupFirstLine(int position) {
                return s.get(position).substring(0,1).toUpperCase();
            }
        }));
