package net.kkolyan.json2.rpc;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * @author nplekhanov
 */
public class ShellServlet extends HttpServlet {
    public static final String SHELL_BEAN_NAME = "ShellServlet.SHELL_BEAN_NAME";
    private Shell shell;

    @Override
    public void init() throws ServletException {
        WebApplicationContext context =
                WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

        String shellBeanName = getInitParameter(SHELL_BEAN_NAME);
        if (shellBeanName != null) {
            shell = context.getBean(shellBeanName, Shell.class);
        } else {
            shell = context.getBean(Shell.class);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Reader reader = req.getReader();
        Writer writer = resp.getWriter();

        shell.execute(reader, writer);

        resp.setContentType("text/javascript");
        resp.setCharacterEncoding("utf8");
        resp.flushBuffer();
    }
}
