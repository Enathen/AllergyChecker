package creativeendlessgrowingceg.allergychecker.design.model;

/**
 * Created by snambiar on 7/07/15.
 */
public class Page {

    String bgColor;
    int    pageNum;
    int    imgResId;
    String txtResId;


    public Page(int pPageNum,String pBgColor,int pImgResId, String txtResId){

        this.bgColor = pBgColor;
        this.pageNum = pPageNum;
        this.imgResId = pImgResId;
        this.txtResId = txtResId;
    }

    public String getTxtResId() {
        return txtResId;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getBgColor() {

        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
}
