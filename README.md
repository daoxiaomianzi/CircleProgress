# CircleProgress
自定义圆弧刻度，和市场上大部分信用额度的圆弧显示差不多，这是公司项目中用到，单独抽出来。
自定义属性attrs已经set方法api及释义如下：
-----------------------

|        Attribute |      default value     |           xml            |                 java          |       释义            | 
|------------------|------------------------|--------------------------|-------------------------------|-----------------|
| arcDistance  |mPdDistance  | arcDistance| setmPdDistance(int mPdDistance)|            圆弧两边的距离
| arcBgColor   |mArcBgColor  | arcBgColor| setmArcBgColor(int mArcBgColor)|   背景圆弧的颜色
| arcForeEndColor |mArcForeEndColor| arcForeEndColor| setmArcForeEndColor(int mArcForeEndColor)|前景圆弧渐变结束时的颜色
| arcForeStartColor|mArcForeStartColor| arcForeStartColor| setmArcForeStartColor(int mArcForeStartColor)|前景圆弧渐变开始时候的颜色
| progressTextRunColor|mProgressTextRunColor|progressTextRunColor|setmProgressTextRunColor(int mProgressTextRunColor)|进度文字颜色
| dottedDefaultColor|mDottedDefaultColor| dottedDefaultColor|setmDottedDefaultColor(int mDottedDefaultColor)|虚线默认颜色
| dottedRunColor  |mDottedRunColor| dottedRunColor|setmDottedRunColor(int mDottedRunColor)|虚线变动颜色
| dottedLineCount |mDottedLineCount| dottedLineCount|setmDottedLineCount(int mDottedLineCount)|画多少线条数
| dottedLineWidth |mDottedLineWidth| dottedLineWidth|setmDottedLineWidth(int mDottedLineWidth)|线条宽度
| dottedLineHeight  |mDottedLineHeight| dottedLineHeight|setmDottedLineHeight(int mDottedLineHeight)|线条高度
| lineDistance|mLineDistance| lineDistance|setmLineDistance(int mLineDistance)|圆弧和虚线之间的距离
| progressMax |mProgressMax| progressMax|setmProgressMax(int mProgressMax)|进度条最大值
| progressTextSize  |mProgressTextSize| progressTextSize|setmProgressTextSize(int mProgressTextSize)|进度文字大小
| progressDesc |mProgressDesc| progressDesc|setmProgressDesc(String mProgressDesc)|进度文字描述
| arcUseGradient  |useGradient| arcUseGradient|setUseGradient(boolean useGradient)|是否使用渐变

-----------------------------
效果图：
![xiaoguotu](https://user-images.githubusercontent.com/20313516/155060661-f1bb62e3-0126-4cf3-8afe-0413edaa69f8.png)

