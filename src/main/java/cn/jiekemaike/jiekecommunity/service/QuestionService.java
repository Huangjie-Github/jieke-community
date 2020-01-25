package cn.jiekemaike.jiekecommunity.service;

import cn.jiekemaike.jiekecommunity.dto.PaginationDTO;
import cn.jiekemaike.jiekecommunity.dto.QuestionDTO;
import cn.jiekemaike.jiekecommunity.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO listPage(Integer page,Integer size){
        PaginationDTO paginationDTO = new PaginationDTO();
        ArrayList<QuestionDTO> listPage = questionMapper.listPage((page-1)*size, size);//页面内容
        Integer listSize = questionMapper.listSize();//总条数
        Integer pageSize = (int) Math.ceil(listSize / (double)size);//最长页数
        paginationDTO.setListPage(listPage);
        paginationDTO.setPage(page);
        if (pageSize<=5){
            for (int i = 1;i<=pageSize;i++){
                paginationDTO.getPageSize().add(i);
            }
        }else if (page<=3){
            for (int i = 1;i<=5;i++){
                paginationDTO.getPageSize().add(i);
            }
        }else if (page>=pageSize-2){
            for (int i = pageSize-4;i<=pageSize;i++){
                paginationDTO.getPageSize().add(i);
            }
        }else if (page<pageSize-2){
            for (int i=page-2;i<=page+2;i++){
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
