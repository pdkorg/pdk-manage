<%--
  Created by IntelliJ IDEA.
  User: hubo
  Date: 2015/8/8
  Time: 19:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="page-sidebar-wrapper">
    <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
    <!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
    <div class="page-sidebar navbar-collapse collapse">
        <ul class="page-sidebar-menu " data-keep-expanded="false" data-auto-scroll="false" data-slide-speed="200">

            <c:forEach var="func" items="${sessionScope.funcList}" varStatus="status">
                <c:choose>
                    <c:when test="${status.first}">
                        <li class="start">
                    </c:when>
                    <c:otherwise>
                        <li>
                    </c:otherwise>
                </c:choose>
                <a id="${func.code}" href="${func.href}<c:if test="${func.href != 'javascript:void(0)' && func.href != '/'}">?funcActiveCode=${func.code}</c:if>">
                    <i class="${func.icon}"></i>
                    <span class="title">${func.name}</span>
                    <c:if test="${func.children.size() > 0}">
                        <span class="arrow"></span>
                    </c:if>
                </a>

                <c:if test="${func.children.size() > 0}">
                    <ul class="sub-menu">
                        <c:forEach var="child" items="${func.children}">
                            <li>
                                <a id="${child.code}" href="${child.href}?funcActiveCode=${child.code}">
                                    <i class="${child.icon}"></i>
                                    ${child.name}
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>