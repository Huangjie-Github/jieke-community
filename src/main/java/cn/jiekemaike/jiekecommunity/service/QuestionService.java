package cn.jiekemaike.jiekecommunity.service;

import cn.jiekemaike.jiekecommunity.dto.PaginationDTO;
import cn.jiekemaike.jiekecommunity.dto.QuestionDTO;
import cn.jiekemaike.jiekecommunity.exception.CustomizeErrorCode;
import cn.jiekemaike.jiekecommunity.exception.CustomizeException;
import cn.jiekemaike.jiekecommunity.mapper.QuestionMapper;
import cn.jiekemaike.jiekecommunity.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:/application.properties")
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Value("${index.problem.pageButtonSize}")
    private Integer pageButtonSize;

    private void pageLabel(PaginationDTO<QuestionDTO> paginationDTO, Integer pageSize, Integer page) {
        if (page < 1)
            page = 1;
        if (page > pageSize)
            page = pageSize;


        if (pageSize <= pageButtonSize) {//总页面数不足规定数目，显示全部的
            for (int i = 1; i <= pageSize; i++) {
                paginationDTO.getPageSize().add(i);
            }
        } else if (page <= Math.ceil(pageButtonSize / 2d)) {//显示开始的页面按钮的长度
            for (int i = 1; i <= 5; i++) {
                paginationDTO.getPageSize().add(i);
            }
        } else if (page >= pageSize - (pageButtonSize / 2)) {//显示最后的页面按钮组
            for (int i = pageSize - pageButtonSize + 1; i <= pageSize; i++) {
                paginationDTO.getPageSize().add(i);
            }
        } else if (page < pageSize - (pageButtonSize / 2)) {//显示中间部位的按钮
            for (int i = page - (pageButtonSize / 2); i <= page + (pageButtonSize / 2); i++) {
                paginationDTO.getPageSize().add(i);
            }
        }
        if (pageSize==1){
            paginationDTO.setShowPrevious(false);//上一行
            paginationDTO.setShowNext(false);
            paginationDTO.setShowFirstPage(false);
            paginationDTO.setShowEndPage(false);
        }else if (page == 1) {
            paginationDTO.setShowPrevious(false);//上一行
            paginationDTO.setShowNext(true);//下一行
        } else if (page > 1 && page < pageSize) {
            paginationDTO.setShowPrevious(true);
            paginationDTO.setShowNext(true);
        } else {
            paginationDTO.setShowPrevious(true);
            paginationDTO.setShowNext(false);
        }
//        是否显示返回首页
        if (paginationDTO.getPageSize().contains(1)) {
            paginationDTO.setShowFirstPage(false);
        } else {
            paginationDTO.setShowFirstPage(true);
        }
//        是否显示返回首页尾页
        if (paginationDTO.getPageSize().contains(pageSize)) {
            paginationDTO.setShowEndPage(false);
        } else {
            paginationDTO.setShowEndPage(true);
        }
    }

    public PaginationDTO<QuestionDTO> listPage(Integer page, Integer size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        ArrayList<QuestionDTO> listPage = questionMapper.listPage((page - 1) * size, size);//页面内容

        if (listPage.size() == 0) {
            System.out.println("页面问题");
            throw new CustomizeException(CustomizeErrorCode.YE_MIAN_BU_CUN_ZAI);
        }

        Integer listSize = questionMapper.listSize();//总条数
        Integer pageSize = (int) Math.ceil(listSize / (double) size);//最长页数
        paginationDTO.setPages(pageSize);
        paginationDTO.setDate(listPage);
        paginationDTO.setPage(page);
        pageLabel(paginationDTO, pageSize, page);
        return paginationDTO;
    }

    public PaginationDTO<QuestionDTO> proFilePage(Integer page, Integer size, Long id) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        if (questionMapper.proFileListPage((page - 1) * size, size, id).size() == 0) {
            throw new CustomizeException(CustomizeErrorCode.YE_MIAN_BU_CUN_ZAI);
        }

        Integer listSize = questionMapper.proFileListPageSize(id);//总条数
        Integer pageSize = (int) Math.ceil(listSize / (double) size);//总页数
        paginationDTO.setPage(page);
        paginationDTO.setPages(pageSize);
        paginationDTO.setDate(questionMapper.proFileListPage((page - 1) * size, size, id));

        pageLabel(paginationDTO, pageSize, page);
        return paginationDTO;
    }

    public QuestionDTO findById(Long id) {
        QuestionDTO questionDTO = questionMapper.findById(id);
        if (questionDTO == null)
            throw new CustomizeException(CustomizeErrorCode.YE_MIAN_BU_CUN_ZAI);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        } else {
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.updateQuestion(question);
        }
    }

    public void updateView(Long id) {
        questionMapper.updateView(id);
    }

    public List<QuestionDTO> selectRegexp(Long id, String tag) {
        if (tag.equals(""))
            return new ArrayList<>();

        List<QuestionDTO> relatedIssues = questionMapper.selectRegexp(id, tag);
        return relatedIssues;
    }
}
