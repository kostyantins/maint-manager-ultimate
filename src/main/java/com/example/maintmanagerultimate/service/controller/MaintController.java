package com.example.maintmanagerultimate.service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaintController {

    @PostMapping("/maint")
    public String createMaint(String createMaint) {
//        final var maintDao = new MaintDao(entityManagerFactory);
//
//        final var comment = request.getParameter("comment");
//
//        try {
//            final var maint = Maint.builder()
//                    .maintIdentifier(request.getParameter("maintIdentifier"))
//                    .capabilityId(Long.valueOf(request.getParameter("capabilityId")))
//                    .createdData(LocalDate.now())
//                    .dueData(LocalDate.parse(request.getParameter("dueData"), ofPattern("d-MM-yyyy")))
//                    .solvePriorityId(Integer.valueOf(request.getParameter("solvePriorityId")))
//                    .estimate(Integer.valueOf(request.getParameter("estimate")))
//                    .client(request.getParameter("client"))
//                    .fixVersion(request.getParameter("fixVersion"))
//                    .build();
//
//            maintDao.save(maint);
//
//            if (comment != null && !comment.isEmpty()) {
//                final var maintCommentDao = new MaintCommentDao(entityManagerFactory);
//                final var maintComment = new MaintComments(comment, maint);
//                maintCommentDao.save(maintComment);
//
//                response.getWriter().write(format("New maint was added: '%s' with comment: %s", maint, maintComment));
//
//                return;
//            }
//
//            response.setStatus(SC_CREATED);
//            response.getWriter().write(format("New maint was added: '%s'", maint));
//        } catch (Exception e) {
//            response.setStatus(SC_BAD_REQUEST);
//            response.getWriter().write(format("Maint was not added due to: '%s'", e.getMessage()));
//        }
        return "";
    }
}
