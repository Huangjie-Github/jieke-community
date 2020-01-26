package cn.jiekemaike.jiekecommunity.service;

import cn.jiekemaike.jiekecommunity.dto.PaginationDTO;
import cn.jiekemaike.jiekecommunity.dto.QuestionDTO;
import cn.jiekemaike.jiekecommunity.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Value("${index.problem.pageButtonSize}")
    private Integer pageButtonSize;

    public PaginationDTO listPage(Integer page,Integer size){

        PaginationDTO paginationDTO = new PaginationDTO();
        ArrayList<QuestionDTO> listPage = questionMapper.listPage((page-1)*size, size);//页面内容
        Integer listSize = questionMapper.listSize();//总条数
        Integer pageSize = (int) Math.ceil(listSize / (double)size);//最长页数
        paginationDTO.setPages(pageSize);
        paginationDTO.setListPage(listPage);
        paginationDTO.setPage(page);

        if (pageSize<=pageButtonSize){//总页面数不足规定数目，显示全部的
            for (int i = 1;i<=pageSize;i++){
                paginationDTO.getPageSize().add(i);
            }
        }else if (page<=Math.ceil(pageButtonSize/2d)){//显示开始的页面按钮的长度
            for (int i = 1;i<=5;i++){
                paginationDTO.getPageSize().add(i);
            }
        }else if (page>=pageSize-(pageButtonSize/2)){//显示最后的页面按钮组
            for (int i = pageSize-pageButtonSize+1;i<=pageSize;i++){
                paginationDTO.getPageSize().add(i);
            }
        }else if (page<pageSize-(pageButtonSize/2)){//显示中间部位的按钮
            for (int i=page-(pageButtonSize/2);i<=page+(pageButtonSize/2);i++){
                paginationDTO.getPageSize().add(i);
            }
        }

        if (page==1){
            paginationDTO.setShowPrevious(false);//上一行
            paginationDTO.setShowNext(true);//下一行
        }else if (page>1&&page<pageSize){
            paginationDTO.setShowPrevious(true);
            paginationDTO.setShowNext(true);
        }else {
            paginationDTO.setShowPrevious(true);
            paginationDTO.setShowNext(false);
        }

        if (paginationDTO.getPageSize().contains(1)){
            paginationDTO.setShowFirstPage(false);
        }else {
            paginationDTO.setShowFirstPage(true);
        }
        if (paginationDTO.getPageSize().contains(pageSize)){
            paginationDTO.setShowEndPage(false);
        }else {
            paginationDTO.setShowEndPage(true);
        }
        return paginationDTO;
    }
}
