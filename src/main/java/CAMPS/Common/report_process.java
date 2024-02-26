package CAMPS.Common;

import CAMPS.Connect.DBConnect;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class report_process {

    public String report_v1(String rm_id, ArrayList<String> attribute) {
        DBConnect db = new DBConnect();
        String query = "", display_type = "", data = "", title_text="";
        try {
            db.getConnection();
            db.read("SELECT rm.report_query,rm.replace_var_count,rm.display_type FROM camps.report_master rm WHERE rm.rm_id='" + rm_id + "'");
            if (db.rs.next()) {
                query = db.rs.getString("report_query");
                display_type = db.rs.getString("display_type");
                if (db.rs.getInt("replace_var_count") != attribute.size()) {
                    return "Error Code: 1091";
                }
            }
            else return "Error Code: 1092";
            for (int i = 1; i <= attribute.size(); i++) {
                query = query.replaceAll("__" + i + "__", attribute.get(i - 1));
            }
            db.read(query);
            if (db.rs.next()) {
                db.rs.beforeFirst();
                db.read1("SELECT ra.ra_query_name,ra.ra_display_name,ra.ra_title_name FROM camps.report_attribute ra WHERE ra.rm_id='" + rm_id + "' and ra.status>0 ORDER BY ra.order_no");
                if (display_type.equalsIgnoreCase("table")) {
                    data += "<div class=\"table-responsive text-nowrap\"><table class='table table-bordered table-striped' width='100%'><thead><tr>";
                    while (db.rs1.next()) {
                        data += "<th>" + db.rs1.getString("ra_display_name") + "</th>";
                    }
                    data += "</tr></thead>";
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        data += "<tr>";
                        while (db.rs1.next()) {
                        	if (db.rs1.getString("ra_title_name")!=null) title_text=db.rs.getString(db.rs1.getString("ra_title_name")); else title_text="";
                            data += "<td title=\""+title_text+"\" data-column=\"" + db.rs1.getString("ra_display_name") + "\">" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</td>";
                        }
                        data += "</tr>";
                    }
                    data += "</table>"+"<!--"+rm_id+":"+query+"-->";
                } else if (display_type.equalsIgnoreCase("data")) {
                    //data += "<table class='tbl_3col' width='100%'>";
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        while (db.rs1.next()) {
                            data += "<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\" style=\"margin-bottom: 5px;\"><span width='15%' style='word-break:break-all;display: inline-grid;width: 40%;'>" + db.rs1.getString("ra_display_name") + "</span><span width='5%' style='display: inline-grid;width: 5%;'>:</span><span width='35%' style='display: inline-grid;'><b>" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</b></span></div>";
                            if (db.rs1.next()) {
                                data += "<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\" style=\"margin-bottom: 5px;\"><span width='15%' style='word-break:break-all;display: inline-grid;width: 40%;'>" + db.rs1.getString("ra_display_name") + "</span><span width='5%' style='display: inline-grid;width: 5%;'>:</span><span width='35%' style='display: inline-grid;'><b>" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</b></span></div>";
                            }
                            //data += "</tr>";
                        }
                    }
                }
                data += "</div><script>function comparer(index) {    return function(a, b) {        var valA = getCellValue(a, index); valB = getCellValue(b, index);        return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.toString().localeCompare(valB);    }}function getCellValue(row, index){ return $(row).children('td').eq(index).text(); } $('th:not(:first-child)').click(function(){    var table = $(this).parents('table').eq(0);    var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()));    this.asc = !this.asc;    if (!this.asc){rows = rows.reverse();}    for (var i = 0; i < rows.length; i++){        $(rows[i]).find('td:first').text(i + 1); table.append(rows[i]);    }})</script>";
            } else {
                data = "No Record Found."+"<!--"+rm_id+":"+query+"-->";
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                db.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(report_process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    public String report_v1_1col(String rm_id, ArrayList<String> attribute) {
        DBConnect db = new DBConnect();
        String query = "", display_type = "", data = "";
        try {
            db.getConnection();
            db.read("SELECT rm.report_query,rm.replace_var_count,rm.display_type FROM camps.report_master rm WHERE rm.rm_id='" + rm_id + "'");
            if (db.rs.next()) {
                query = db.rs.getString("report_query");
                display_type = db.rs.getString("display_type");
                if (db.rs.getInt("replace_var_count") != attribute.size()) {
                    return "";
                }
            }
            for (int i = 1; i <= attribute.size(); i++) {
                query = query.replaceAll("__" + i + "__", attribute.get(i - 1));
            }
            db.read(query);
            if (db.rs.next()) {
                db.rs.beforeFirst();
                db.read1("SELECT ra.ra_query_name,ra.ra_display_name FROM camps.report_attribute ra WHERE ra.rm_id='" + rm_id + "' and ra.status>0 ORDER BY ra.order_no");
                if (display_type.equalsIgnoreCase("table")) {
                    data += "<div class=\"table-responsive text-nowrap\"><table class='table table-bordered table-striped' width='100%'><thead><tr>";
                    while (db.rs1.next()) {
                        data += "<th>" + db.rs1.getString("ra_display_name") + "</th>";
                    }
                    data += "</tr></thead>";
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        data += "<tr>";
                        while (db.rs1.next()) {
                            data += "<td data-column=\"" + db.rs1.getString("ra_display_name") + "\">" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</td>";
                        }
                        data += "</tr>";
                    }
                    data += "</table>";
                } else if (display_type.equalsIgnoreCase("data")) {
                    //data += "<table class='tbl_3col' width='100%'>";
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        while (db.rs1.next()) {
                            data += "<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\" style=\"margin-bottom: 5px;\"><span width='15%' style='word-break:break-all;display: inline-grid;width: 40%;'>" + db.rs1.getString("ra_display_name") + "</span><span width='5%' style='display: inline-grid;width: 5%;'>:</span><span width='35%' style='display: inline-grid;'><b>" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</b></span></div>";
                            if (db.rs1.next()) {
                                data += "<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\" style=\"margin-bottom: 5px;\"><span width='15%' style='word-break:break-all;display: inline-grid;width: 40%;'>" + db.rs1.getString("ra_display_name") + "</span><span width='5%' style='display: inline-grid;width: 5%;'>:</span><span width='35%' style='display: inline-grid;'><b>" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</b></span></div>";
                            }
                            //data += "</tr>";
                        }
                    }
                }
                data += "</div><script>function comparer(index) {    return function(a, b) {        var valA = getCellValue(a, index); valB = getCellValue(b, index);        return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.toString().localeCompare(valB);    }}function getCellValue(row, index){ return $(row).children('td').eq(index).text(); } $('th:not(:first-child)').click(function(){    var table = $(this).parents('table').eq(0);    var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()));    this.asc = !this.asc;    if (!this.asc){rows = rows.reverse();}    for (var i = 0; i < rows.length; i++){        $(rows[i]).find('td:first').text(i + 1); table.append(rows[i]);    }})</script>";
            } else {
                data = "No Record Found";
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                db.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(report_process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    public String report_v1_withoutlabel(String rm_id, ArrayList<String> attribute) {
        DBConnect db = new DBConnect();
        String query = "", display_type = "", data = "";
        try {
            db.getConnection();
            db.read("SELECT rm.report_query,rm.replace_var_count,rm.display_type FROM camps.report_master rm WHERE rm.rm_id='" + rm_id + "'");
            if (db.rs.next()) {
                query = db.rs.getString("report_query");
                display_type = db.rs.getString("display_type");
                if (db.rs.getInt("replace_var_count") != attribute.size()) {
                    return "";
                }
            }
            for (int i = 1; i <= attribute.size(); i++) {
                query = query.replaceAll("__" + i + "__", attribute.get(i - 1));
            }
            db.read(query);
            if (db.rs.next()) {
                db.rs.beforeFirst();
                db.read1("SELECT ra.ra_query_name,ra.ra_display_name FROM camps.report_attribute ra WHERE ra.rm_id='" + rm_id + "' and ra.status>0 ORDER BY ra.order_no");
                if (display_type.equalsIgnoreCase("table")) {
                    data += "<div class=\"table-responsive text-nowrap\"><table class='table table-bordered table-striped' width='100%'><thead><tr>";
                    while (db.rs1.next()) {
                        data += "<th>" + db.rs1.getString("ra_display_name") + "</th>";
                    }
                    data += "</tr></thead>";
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        data += "<tr>";
                        while (db.rs1.next()) {
                            data += "<td data-column=\"" + db.rs1.getString("ra_display_name") + "\">" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</td>";
                        }
                        data += "</tr>";
                    }
                    data += "</table>";
                } else if (display_type.equalsIgnoreCase("data")) {
                    //data += "<table class='tbl_3col' width='100%'>";
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        while (db.rs1.next()) {
                            data += "<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\" style=\"margin-bottom: 5px;\"><span width='100%' style='display: inline-grid;'><b>" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</b></span></div>";
                            
                            //data += "</tr>";
                        }
                    }
                }
                data += "</div><script>function comparer(index) {    return function(a, b) {        var valA = getCellValue(a, index); valB = getCellValue(b, index);        return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.toString().localeCompare(valB);    }}function getCellValue(row, index){ return $(row).children('td').eq(index).text(); } $('th:not(:first-child)').click(function(){    var table = $(this).parents('table').eq(0);    var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()));    this.asc = !this.asc;    if (!this.asc){rows = rows.reverse();}    for (var i = 0; i < rows.length; i++){        $(rows[i]).find('td:first').text(i + 1); table.append(rows[i]);    }})</script>";
            } else {
                data = "No Record Found";
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                db.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(report_process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    public String report_unformat_v1(String rm_id, ArrayList<String> attribute, int header) {
        DBConnect db = new DBConnect();
        String query = "", display_type = "", data = "";
        try {
            db.getConnection();
            db.read("SELECT rm.report_query,rm.replace_var_count,rm.display_type FROM camps.report_master rm WHERE rm.rm_id='" + rm_id + "'");
            if (db.rs.next()) {
                query = db.rs.getString("report_query");
                display_type = db.rs.getString("display_type");
                if (db.rs.getInt("replace_var_count") != attribute.size()) {
                    return "";
                }
            }
            for (int i = 1; i <= attribute.size(); i++) {
                query = query.replaceAll("__" + i + "__", attribute.get(i - 1));
            }
            db.read(query);
            if (db.rs.next()) {
                db.rs.beforeFirst();
                db.read1("SELECT ra.ra_query_name,ra.ra_display_name FROM camps.report_attribute ra WHERE ra.rm_id='" + rm_id + "' and ra.status>0 ORDER BY ra.order_no");
                if (display_type.equalsIgnoreCase("table")) {
                    data += "<div class=\"table-responsive text-nowrap\"><table class='table table-bordered table-striped' width='100%'>";
                    if (header == 1) {
                        data += "<thead><tr>";
                        while (db.rs1.next()) {
                            data += "<th>" + db.rs1.getString("ra_display_name") + "</th>";
                        }
                        data += "</tr></thead>";
                    }
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        data += "<tr>";
                        while (db.rs1.next()) {
                            data += "<td data-column=\"" + db.rs1.getString("ra_display_name") + "\">" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</td>";
                        }
                        data += "</tr>";
                    }
                    data += "</table></div>";
                } else if (display_type.equalsIgnoreCase("data")) {
                    //data += "<table class='tbl_3col' width='100%'>";
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        while (db.rs1.next()) {
                            data += "" + db.rs.getString(db.rs1.getString("ra_query_name")) + "";
                            
                            //data += "</tr>";
                        }
                    }
                }
                //data += "</div>";
            } else {
                //data = "No Record Found";
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                db.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(report_process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    

    public String report_dynamic_v1(String rm_id, ArrayList<String> attribute) {
        DBConnect db = new DBConnect();
        String query = "", display_type = "", data = "";
        try {
            db.getConnection();
            db.read("SELECT rm.report_query,rm.replace_var_count,rm.display_type FROM camps.report_master rm WHERE rm.rm_id='" + rm_id + "'");
            if (db.rs.next()) {
                query = db.rs.getString("report_query");
                display_type = db.rs.getString("display_type");
                if (db.rs.getInt("replace_var_count") != attribute.size()) {
                    return "";
                }
            }
            for (int i = 1; i <= attribute.size(); i++) {
                query = query.replaceAll("__" + i + "__", attribute.get(i - 1));
            }
            db.read(query);
            if (db.rs.next()) {
                db.read(db.rs.getString("query"));
            }
            if (db.rs.next()) {
                db.rs.beforeFirst();
                db.read1("SELECT ra.ra_query_name,ra.ra_display_name FROM camps.report_attribute ra WHERE ra.rm_id='" + rm_id + "' and ra.status>0 ORDER BY ra.order_no");
                if (display_type.equalsIgnoreCase("table")) {
                    data += "<div class=\"table-responsive text-nowrap\"><table class='table table-bordered table-striped' width='100%'><thead><tr>";
                    ResultSetMetaData resultSetMetaData = db.rs.getMetaData();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                        data += "<th>" + resultSetMetaData.getColumnLabel(i) + "</th>";
                    }
                    data += "</tr></thead>";
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        data += "<tr>";
                        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                            data += "<td data-column=\"" + resultSetMetaData.getColumnName(i) + "\">" + db.rs.getString(resultSetMetaData.getColumnLabel(i)) + "</td>";
                        }
                        data += "</tr>";
                    }
                } else if (display_type.equalsIgnoreCase("data")) {
                    //data += "<table class='tbl_3col' width='100%'>";
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        while (db.rs1.next()) {
                            data += "<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\"><span width='15%' style='word-break:break-all;display: inline-grid;width: 40%;'>" + db.rs1.getString("ra_display_name") + "</span><span width='1%' style='display: inline-grid;width: 1%;'>:</span><span width='35%' style='display: inline-grid;'><b>" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</b></span></div>";
                            if (db.rs1.next()) {
                                data += "<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\"><span width='15%' style='word-break:break-all;display: inline-grid;width: 40%;'>" + db.rs1.getString("ra_display_name") + "</span><span width='1%' style='display: inline-grid;width: 1%;'>:</span><span width='35%' style='display: inline-grid;'><b>" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</b></span></div>";
                            }
                            //data += "</tr>";
                        }
                    }
                }
                data += "</div><script>function comparer(index) {    return function(a, b) {        var valA = getCellValue(a, index); valB = getCellValue(b, index);        return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.toString().localeCompare(valB);    }}function getCellValue(row, index){ return $(row).children('td').eq(index).text(); } $('th:not(:first-child)').click(function(){    var table = $(this).parents('table').eq(0);    var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()));    this.asc = !this.asc;    if (!this.asc){rows = rows.reverse();}    for (var i = 0; i < rows.length; i++){        $(rows[i]).find('td:first').text(i + 1); table.append(rows[i]);    }})</script>";
            } else {
                data = "No Record Found";
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                db.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(report_process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    public String report_custom_v1(String rm_id, ArrayList<String> attribute, String custom_list) {
        DBConnect db = new DBConnect();
        String query = "", display_type = "", data = "";
        try {
            db.getConnection();
            db.read("SELECT rm.report_query,rm.replace_var_count,rm.display_type FROM camps.report_master rm WHERE rm.rm_id='" + rm_id + "'");
            if (db.rs.next()) {
                query = db.rs.getString("report_query");
                display_type = db.rs.getString("display_type");
                if (db.rs.getInt("replace_var_count") != attribute.size()) {
                    return "";
                }
            }
            for (int i = 1; i <= attribute.size(); i++) {
                query = query.replaceAll("__" + i + "__", attribute.get(i - 1));
            }
            db.read(query);
            if (db.rs.next()) {
                db.rs.beforeFirst();
                db.read1("SELECT ra.ra_query_name,ra.ra_display_name FROM camps.report_attribute ra WHERE ra.rm_id='" + rm_id + "' AND ra.ra_id IN('" + custom_list + "')  AND ra.status>0 ORDER BY FIELD(ra.ra_id,'" + custom_list + "')");
                if (display_type.equalsIgnoreCase("table")) {
                    data += "<div class=\"table-responsive text-nowrap\"><table class='table table-bordered' width='100%'><thead><tr><td>S No.</td>";
                    while (db.rs1.next()) {
                        data += "<th>" + db.rs1.getString("ra_display_name") + "</th>";
                    }
                    data += "</tr></thead>";
                    int sno=1;
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        data += "<tr><td>"+ sno++ +"</td>";
                        while (db.rs1.next()) {
                            data += "<td data-column=\"" + db.rs1.getString("ra_display_name") + "\">" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</td>";
                        }
                        data += "</tr>";
                    }
                } else if (display_type.equalsIgnoreCase("data")) {
                    data += "<table class='tbl_3col' width='100%'>";
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        while (db.rs1.next()) {
                            data += "<tr><td width='15%' style='word-break:break-all;'>" + db.rs1.getString("ra_display_name") + "</td><td width='1%'>:</td><td width='35%' style='word-break:break-all;'><b>" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</b></td>";
                            if (db.rs1.next()) {
                                data += "<td width='15%' style='word-break:break-all;'>" + db.rs1.getString("ra_display_name") + "</td><td width='1%'>:</td><td width='35%' style='word-break:break-all;'><b>" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</b></td>";
                            }
                            data += "</tr>";
                        }
                    }
                }
                data += "</table></div>";
            } else {
                data = "No Record Found";
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                db.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(report_process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    public String report_custom_v2(String rm_id, ArrayList<String> attribute, String custom_list) {
        DBConnect db = new DBConnect();
        String query = "", qry2 = " Select concat_ws('</td><td>'", display_type = "", data = "";
        try {
            db.getConnection();
            db.read("SELECT rm.report_query,rm.replace_var_count,rm.display_type FROM camps.report_master rm WHERE rm.rm_id='" + rm_id + "'");
            if (db.rs.next()) {
                query = db.rs.getString("report_query");
                display_type = db.rs.getString("display_type");
                if (db.rs.getInt("replace_var_count") != attribute.size()) {
                    return "";
                }
            }
            for (int i = 1; i <= attribute.size(); i++) {
                query = query.replaceAll("__" + i + "__", attribute.get(i - 1));
            }
            db.read(query);
            if (db.rs.next()) {
                db.rs.beforeFirst();
                db.read1("SELECT ra.ra_query_name,ra.ra_display_name FROM camps.report_attribute ra WHERE ra.rm_id='" + rm_id + "' AND ra.ra_id IN('" + custom_list + "')  AND ra.status>0 ORDER BY FIELD(ra.ra_id,'" + custom_list + "')");
                if (display_type.equalsIgnoreCase("table")) {
                    data += "<div class=\"table-responsive text-nowrap\"><table class='table table-bordered' width='100%'><thead><tr>";
                    while (db.rs1.next()) {
                        data += "<th>" + db.rs1.getString("ra_display_name") + "</th>";
                        qry2 += "," + db.rs1.getString("ra_query_name");
                    }
                    data += "</tr></thead>";
                    db.read(qry2 + ") tab_data from (" + query + ") a");
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        data += "<tr>";
                        // while (db.rs1.next()) {
                        data += "<td>" + db.rs.getString("tab_data") + "</td>";
                        //}
                        data += "</tr>";
                    }
                } else if (display_type.equalsIgnoreCase("data")) {
                    data += "<table class='tbl_3col' width='100%'>";
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        while (db.rs1.next()) {
                            data += "<tr><td width='15%' style='word-break:break-all;'>" + db.rs1.getString("ra_display_name") + "</td><td width='1%'>:</td><td width='35%' style='word-break:break-all;'><b>" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</b></td>";
                            if (db.rs1.next()) {
                                data += "<td width='15%' style='word-break:break-all;'>" + db.rs1.getString("ra_display_name") + "</td><td width='1%'>:</td><td width='35%' style='word-break:break-all;'><b>" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</b></td>";
                            }
                            data += "</tr>";
                        }
                    }
                }
                data += "</table></div>";
            } else {
                data = "No Record Found";
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                db.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(report_process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    public String json_v1(String rm_id, ArrayList<String> attribute, HttpServletRequest request) {
        DBConnect db = new DBConnect();
        String query = "", tot_query = "", filter_query = "", order_by = " ORDER BY ", where_condition = "", display_type = "", data = "", json = "";
        int wc_count = 0, tot_record = 0, filter_record = 0;
        try {
            db.getConnection();
            db.read("SELECT rm.report_query,rm.replace_var_count,rm.display_type,GROUP_CONCAT(ra.ra_query_name order by ra.order_no SEPARATOR ',''''),ifnull(' ) q_data  FROM camps.report_master rm INNER JOIN camps.report_attribute ra ON rm.rm_id=ra.rm_id AND ra.status>0 WHERE rm.rm_id='" + rm_id + "'");
            if (db.rs.next() && db.rs.getString("display_type").equalsIgnoreCase("json")) {
                tot_query = db.rs.getString("report_query").replaceAll("__JSON__", "count(*) t_count");
                query = db.rs.getString("report_query").replaceAll("__JSON__", " JSON_ARRAY(ifnull(" + db.rs.getString("q_data") + ",''))datacol,ifnull(" + db.rs.getString("q_data") + ",'') ");
                display_type = db.rs.getString("display_type");
                if (db.rs.getInt("replace_var_count") != attribute.size()) {
                    return "";
                }
            }
            db.read1("SELECT ra.ra_query_name,ra.ra_display_name FROM camps.report_attribute ra WHERE ra.rm_id='" + rm_id + "' and ra.status>0 ORDER BY ra.order_no");
            while (db.rs1.next()) {
                where_condition += " AND " + db.rs1.getString("ra_query_name") + " like '%" + request.getParameter("columns[" + wc_count++ + "][search][value]") + "%' ";
                if (request.getParameter("order[0][column]").equalsIgnoreCase(String.valueOf(wc_count - 1))) {
                    order_by += "" + db.rs1.getString("ra_query_name") + " " + request.getParameter("order[0][dir]") + "";
                }
            }
            for (int i = 1; i <= attribute.size(); i++) {
                query = query.replaceAll("__" + i + "__", attribute.get(i - 1));
            }
            query = query.replaceAll("__WC__", where_condition);
            query = query.replaceAll("__OB__", order_by);
            filter_query = tot_query.replaceAll("__LB__", "  ").replaceAll("__WC__", where_condition).replaceAll("__OB__", " ");
            tot_query = tot_query.replaceAll("__LB__", "  ").replaceAll("__WC__", " ").replaceAll("__OB__", " ");
            db.read(tot_query);
            if (db.rs.next()) {
                tot_record = db.rs.getInt("t_count");
            }
            db.read(filter_query);
            if (db.rs.next()) {
                filter_record = db.rs.getInt("t_count");
            }
            query = query.replaceAll("__LB__", " LIMIT " + request.getParameter("start") + ", " + (request.getParameter("length").equalsIgnoreCase("-1") ? filter_record : request.getParameter("length")) + " ");
            db.read(query);
            if (db.rs.next()) {
                data += db.rs.getString("datacol");
            }
            while (db.rs.next()) {
                data += "," + db.rs.getString("datacol");
            }
            json = "{ \"draw\": " + request.getParameter("draw") + ",  \"recordsTotal\": " + tot_record + ", \"recordsFiltered\": " + filter_record + ",\"data\":[" + data + "]}";

        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                db.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(report_process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return json;
    }
    public String adhoc_report(String aqid, HttpSession session) {
		String query = "";
		int cc = 0;
		String data = "";
		DBConnect db = new DBConnect();
		try {
			db.getConnection();
			db.read("SELECT aq.title,aq.query,aq.roles,IFNULL(aq.no_data_text,' '),IFNULL(aq.header_text,' '),IFNULL(aq.footer_text,' '),aq.no_data_text_type,aq.header_text_type,aq.footer_text_type FROM camps.reports_aq aq WHERE aq.raq_id='" + aqid + "'");
			if (db.rs.next()) {
				query = db.rs.getString(2);
				query = query.replaceAll("__ss_id__", session.getAttribute("ss_id").toString());
				query = query.replaceAll("__depId__", session.getAttribute("depId").toString());
				query = query.replaceAll("__user_id__", session.getAttribute("user_id").toString());
				query = query.replaceAll("__user_name__", session.getAttribute("user_name").toString());
				query = query.replaceAll("__uType__", session.getAttribute("uType").toString());
				db.read1(query);
				if (db.rs1.next()) {
					db.rs1.beforeFirst();
					cc = db.rs1.getMetaData().getColumnCount();
					if (db.rs.getString(8).equalsIgnoreCase("text"))
					{
						data+=db.rs.getString(5);
					}
					else if (db.rs.getString(8).equalsIgnoreCase("query"))
					{
						db.read2(db.rs.getString(5));
						if (db.rs2.next())
						{
							data+=db.rs2.getString(1);
						}
					}
					
					data += "<div class=\"tbl_new\" id='table_1'><table class='table table-bordered' width='100%'>";
					
					data += "<thead><tr>";
					for (int i = 1; i <= cc; i++) {
						data += "<th >" + db.rs1.getMetaData().getColumnLabel(i) + "</th>";
					}
					data += "</tr></thead>";
					while (db.rs1.next()) {
						data += "<tr>";
						for (int i = 1; i <= cc; i++) {
							data += "<td data-column='"+db.rs1.getMetaData().getColumnLabel(i)+"'>" + db.rs1.getString(i) + "</td>";
						}
						data += "</tr>";
					}
					data+="</table></div>";
					if (db.rs.getString(9).equalsIgnoreCase("text"))
					{
						data+=db.rs.getString(6);
					}
					else if (db.rs.getString(9).equalsIgnoreCase("query"))
					{
						db.read2(db.rs.getString(6));
						if (db.rs2.next())
						{
							data+=db.rs2.getString(1);
						}
					}

				}
			
				else {
					if (db.rs.getString(7).equalsIgnoreCase("text"))
					{
						data+=db.rs.getString(4);
					}
					else if (db.rs.getString(7).equalsIgnoreCase("query"))
					{
						db.read2(db.rs.getString(4));
						if (db.rs2.next())
						{
							data+=db.rs2.getString(1);
						}
					}
			}
			}
		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
				Logger.getLogger(report_process.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return data ;
	}

	public String documentation_report_query(String em_id, String records) {
		return "";
	}

	public String static_table_report(String rm_id, ArrayList<String> attribute) {
		DBConnect db = new DBConnect();
		String query = "", display_type = "", data = "";
		String no_data_text_type="",no_data_text="", header_text_type="",header_text="",footer_text_type="",footer_text="";
		try {
			db.getConnection();
			db.read("SELECT rm.report_query,rm.replace_var_count,rm.display_type,IFNULL(rm.no_data_text_type,'') no_data_text_type,IFNULL(rm.no_data_text,'') no_data_text,IFNULL(rm.header_text_type,'') header_text_type,IFNULL(rm.header_text,'') header_text,IFNULL(rm.footer_text_type,'') footer_text_type,IFNULL(rm.footer_text,'') footer_text FROM camps.report_master rm WHERE rm.rm_id='"
					+ rm_id + "'");
			if (db.rs.next()) {
				query = db.rs.getString("report_query");
				int b = db.rs.getInt("replace_var_count");
				if (db.rs.getInt("replace_var_count") != attribute.size()) {
					return "";
				}
				no_data_text_type=db.rs.getString("no_data_text_type");
				no_data_text=db.rs.getString("no_data_text");
				header_text_type=db.rs.getString("header_text_type");
				header_text=db.rs.getString("header_text");
				footer_text_type=db.rs.getString("footer_text_type");
				footer_text=db.rs.getString("footer_text");
				
				
			}
			for (int i = 1; i <= attribute.size(); i++) {
				query = query.replaceAll("__" + i + "__", attribute.get(i - 1));
			}
			db.read(query);
			if (db.rs.next()) {
				db.rs.beforeFirst();
				db.read1("SELECT ra.ra_query_name,ra.ra_display_name FROM camps.report_attribute ra WHERE ra.rm_id='"
						+ rm_id + "' and ra.status>0 ORDER BY ra.order_no");
				if (header_text_type.equalsIgnoreCase("text"))
					data+=header_text;
				else if (header_text_type.equalsIgnoreCase("query"))
				{
					db.read2(header_text);
					if (db.rs2.next())
						data+=db.rs2.getString(1);
				}
				data += "<div class=\"tbl_new\" id='table_1'><table class='table table-bordered' width='100%'><thead><tr>";
				while (db.rs1.next()) {
					data += "<th>" + db.rs1.getString("ra_display_name") + "</th>";
				}
				data += "</tr></thead>";
				while (db.rs.next()) {
					db.rs1.beforeFirst();
					data += "<tr>";
					while (db.rs1.next()) {
						data += "<td data-column=\"" + db.rs1.getString("ra_display_name") + ":\">"
								+ db.rs.getString(db.rs1.getString("ra_query_name")) + "</td>";
					}
					data += "</tr>";
				}
				data += "</table>";
				if (footer_text_type.equalsIgnoreCase("text"))
					data+=footer_text;
				else if (footer_text_type.equalsIgnoreCase("query"))
				{
					db.read2(footer_text);
					if (db.rs2.next())
						data+=db.rs2.getString(1);
				}
				data += "</div>";
			} else {
				if (no_data_text_type.equalsIgnoreCase("text"))
					data=no_data_text;
				else if (no_data_text_type.equalsIgnoreCase("query"))
				{
					db.read2(no_data_text);
					if (db.rs2.next())
						data=db.rs2.getString(1);
				}
				else
					data = "No Record Found";
			}
		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
				Logger.getLogger(report_process.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return data;
	}

	

	public String report_master_query(String rm_id) {
		DBConnect db = new DBConnect();
		String query = "", display_type = "", data = "";
		try {
			db.getConnection();
			db.read("SELECT rm.report_query,rm.replace_var_count,rm.display_type FROM camps.report_master rm WHERE rm.rm_id='"
					+ rm_id + "'");
			if (db.rs.next()) {
				data = db.rs.getString(1);
			} else {
				data = "No Record Found";
			}
		} catch (Exception e) {
			return e.toString();
		} finally {
			try {
				db.closeConnection();
			} catch (SQLException ex) {
				Logger.getLogger(report_process.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return data;
	}

    public String report_Filter_Create(String rm_id, ArrayList<String> attribute, HttpServletRequest request) {
        DBConnect db = new DBConnect();
        String query = "", tot_query = "", filter_query = "", order_by = " ORDER BY ", where_condition = "", display_type = "", data = "", json = "";
        int wc_count = 0, tot_record = 0, filter_record = 0;
        try {
            db.getConnection();

        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                db.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(report_process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }
    
    public String report_v2(String rm_id, ArrayList<String> attribute) {
        DBConnect db = new DBConnect();
        String query = "", display_type = "", data = "", title_text="";
        try {
            db.getConnection();
            db.read("SELECT rm.report_query,rm.replace_var_count,rm.display_type FROM camps.report_master rm WHERE rm.rm_id='" + rm_id + "'");
            if (db.rs.next()) {
                query = db.rs.getString("report_query");
                display_type = db.rs.getString("display_type");
                if (db.rs.getInt("replace_var_count") != attribute.size()) {
                    return "Error Code: 1091";
                }
            }
            else return "Error Code: 1092";
            for (int i = 1; i <= attribute.size(); i++) {
                query = query.replaceAll("__" + i + "__", attribute.get(i - 1));
            }
            db.read(query);
            if (db.rs.next()) {
                db.rs.beforeFirst();
                db.read1("SELECT ra.ra_query_name,ra.ra_display_name,ra.ra_title_name FROM camps.report_attribute ra WHERE ra.rm_id='" + rm_id + "' and ra.status>0 ORDER BY ra.order_no");
                if (display_type.equalsIgnoreCase("table")) {
                	 data = "<div class='table-responsive text-wrap'><table class='tbl_new' width=\"100%\" border=\"1\" id=\"datatbl\"><tr>";
                    while (db.rs1.next()) {
                        data += "<th>" + db.rs1.getString("ra_display_name") + "</th>";
                    }
                    data += "</tr>";
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        data += "<tr>";
                        while (db.rs1.next()) {
                        	if (db.rs1.getString("ra_title_name")!=null) title_text=db.rs.getString(db.rs1.getString("ra_title_name")); else title_text="";
                            data += "<td title=\""+title_text+"\" data-column=\"" + db.rs1.getString("ra_display_name") + "\">" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</td>";
                        }
                        data += "</tr>";
                    }
                    data += "</table></div>";
                } else if (display_type.equalsIgnoreCase("data")) {
                    //data += "<table class='tbl_3col' width='100%'>";
                    while (db.rs.next()) {
                        db.rs1.beforeFirst();
                        while (db.rs1.next()) {
                            data += "<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\" style=\"margin-bottom: 5px;\"><span width='15%' style='word-break:break-all;display: inline-grid;width: 40%;'>" + db.rs1.getString("ra_display_name") + "</span><span width='5%' style='display: inline-grid;width: 5%;'>:</span><span width='35%' style='display: inline-grid;'><b>" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</b></span></div>";
                            if (db.rs1.next()) {
                                data += "<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\" style=\"margin-bottom: 5px;\"><span width='15%' style='word-break:break-all;display: inline-grid;width: 40%;'>" + db.rs1.getString("ra_display_name") + "</span><span width='5%' style='display: inline-grid;width: 5%;'>:</span><span width='35%' style='display: inline-grid;'><b>" + db.rs.getString(db.rs1.getString("ra_query_name")) + "</b></span></div>";
                            }
                            //data += "</tr>";
                        }
                    }
                }
                //data += "<script>function comparer(index) {    return function(a, b) {        var valA = getCellValue(a, index); valB = getCellValue(b, index);        return $.isNumeric(valA) && $.isNumeric(valB) ? valA - valB : valA.toString().localeCompare(valB);    }}function getCellValue(row, index){ return $(row).children('td').eq(index).text(); } $('th:not(:first-child)').click(function(){    var table = $(this).parents('table').eq(0);    var rows = table.find('tr:gt(0)').toArray().sort(comparer($(this).index()));    this.asc = !this.asc;    if (!this.asc){rows = rows.reverse();}    for (var i = 0; i < rows.length; i++){        $(rows[i]).find('td:first').text(i + 1); table.append(rows[i]);    }})</script>";
            } else {
                data = "No Record Found.<!--"+rm_id+":"+query+"-->";
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                db.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(report_process.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
}
