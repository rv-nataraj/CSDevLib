<%
switch (request.getParameter("am_c")){
    case "1":
        out.print("<script>showNotification('alert-success', 'Process Completed...','top', 'right',  '', '',false,'glyphicon glyphicon-cd glyphicon-spin');</script>");
        break;
    case "2":
        out.print("<script>showNotification('alert-danger', 'Insufficient Role...','top', 'right',  '', '',false,'glyphicon glyphicon-cd glyphicon-spin');</script>");
        break;
    case "error":
        out.print("<script> showNotification('alert-danger', '"+request.getParameter("error")+"','top', 'right',  '', '',false,'glyphicon glyphicon-thumbs-down');</script>");
        break;
    default:
        out.print("<script> showNotification('alert-danger', '<b title=\""+request.getParameter("am_c")+"\">Some Thing went Wrong!!!<b>','top', 'right',  '', '',false,'glyphicon glyphicon-thumbs-down');</script>");
        break;
}
%>