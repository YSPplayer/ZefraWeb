/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2023-04-01 05:57:36 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html><!--使用html5版本，不加的话效果不一样-->\r\n");
      out.write("<html lang=\"zh-CN\">\r\n");
      out.write("<head>\r\n");
      out.write("    <meta charset=\"UTF-8\">\r\n");
      out.write("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
      out.write("    <title>login</title>\r\n");
      out.write("    <!--导入login的css-->\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/login.css\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("  <!--登录主界面-->\r\n");
      out.write("    <div class=\"login_main\">\r\n");
      out.write("       <!--设置登录界面的左边框图片-->\r\n");
      out.write("        <img src=\"pics/login/01.jpg\"/>\r\n");
      out.write("        <!--登录副页面-->\r\n");
      out.write("         <div class=\"login_sub\">\r\n");
      out.write("         <!--登录提示-->\r\n");
      out.write("         <h2>登录</h2>\r\n");
      out.write("        <form action=\"demo.php\" method=\"POST\" name=\"name1\"> \r\n");
      out.write("         <!--登录副页面 -->\r\n");
      out.write("          <span class=\"login_characters_text\"><h4>账号:</h4></span><input type=\"text\"  maxlength=\"10\"  id=\"login_sub_input\">\r\n");
      out.write("          <span class=\"login_characters_password\"><h4>密码:</h4></span><input type=\"password\" id=\"login_sub_input\">\r\n");
      out.write("          <input type=\"button\" id=\"login_sub_button_sign\" value=\"登录\">\r\n");
      out.write("          <input type=\"button\" id=\"login_sub_button_logon\" value=\"注册\">\r\n");
      out.write("          <span id=\"login_sub_link\"><a href=\"#\">忘记密码?</a></span>\r\n");
      out.write("        </form>\r\n");
      out.write("         <div>\r\n");
      out.write("\r\n");
      out.write("    </div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
