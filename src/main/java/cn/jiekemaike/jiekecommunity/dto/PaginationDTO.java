package cn.jiekemaike.jiekecommunity.dto;

import cn.jiekemaike.jiekecommunity.model.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> date;//所有Item的内容的集合
    private boolean showPrevious;//是否显示前一页按钮
    private boolean showFirstPage;//是否显示第一页按钮
    private boolean showNext;//是否显示下一页按钮
    private boolean showEndPage;//是否显示最后一页按钮
    private Integer page;//当前点击第几页
    private Integer pages;//一共分成多少页
    private List<Integer> pageSize = new ArrayList<>();//分页条目显示的数字
}
