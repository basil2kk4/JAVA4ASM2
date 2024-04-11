/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import DAO.CustomerDAO;
import DAO.UserDAO;
import entity.Customer;
import entity.UserAccount;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Admin
 */
@WebServlet(name = "Servlet", urlPatterns = {"/user/list", "/user/add", "/user/login", "/user/update", "/user/delete", "/user/xoa", "/user/hienthi", "/user/capnhap", "/user/them"})
public class UserAccountServlet extends HttpServlet {

    UserDAO userdao;
    CustomerDAO customerDAO;
    List<UserAccount> datalst = new ArrayList<>();
    List<Customer> datalstcus = new ArrayList<>();
    boolean islogin = false;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void init()
            throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        this.userdao = new UserDAO(util.hibermateUtil.getSessionFactory());
        this.customerDAO = new CustomerDAO(util.hibermateUtil.getSessionFactory());

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.service(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        String url = req.getRequestURI();
        String method = req.getMethod();
        List<String> errors = new ArrayList<>();
        String error = null;
        String action = req.getParameter("action");
        String id = req.getParameter("ip_userId");
        String name = req.getParameter("ip_name");
        String userName = req.getParameter("ip_userName");
        String PassWord = req.getParameter("ip_PassWord");
        List<UserAccount> userAccounts = userdao.GetAllData();
        req.setAttribute("userAccounts", userAccounts);
        List<Customer> Customer = customerDAO.GetdataCustomer();
        req.setAttribute("Customers", Customer);
        if (url.contains("list")) {
            if (islogin == false) {
                resp.sendRedirect(req.getContextPath() + "/user/login");
                return;
            }
            if (method.equals("GET")) {
//                 req.setAttribute("errors", errors);
                req.getRequestDispatcher("/Views/Viewsmanage.jsp").forward(req, resp);
            } else if (method.equals("POST")) {

                UserAccount userAccount = new UserAccount(Integer.parseInt(id), name, userName, PassWord);
                userdao.updateUserAccount(userAccount);
                resp.sendRedirect("/ASM2/user/list");

            }
        } else if (url.contains("add")) {
            if (islogin == false) {
                resp.sendRedirect(req.getContextPath() + "/user/login");
                return;
            }
            if (method.equals("GET")) {
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/Views/Viewsmanage.jsp").forward(req, resp);
            } else if (method.equals("POST")) {
                if (action.equals("add")) {
                    userName = userName.trim();
                    if (name.isEmpty()) {
                        errors.add("Tên không được trống");
                    }
                    if (userName.isEmpty()) {
                        errors.add("Username không được trống");
                    }
                    if (PassWord.isEmpty()) {
                        errors.add("Password không được trống");
                    }
                    if (userdao.getUserByUsername(userName) != null) {
                        errors.add("Username đã tồn tại");
                    }

                    if (!errors.isEmpty()) {
                        req.setAttribute("errors", errors);
                        req.getRequestDispatcher("/Views/Viewsmanage.jsp").forward(req, resp);
                    } else {
                        UserAccount userAccount = new UserAccount(name, userName, PassWord);
                        userdao.AddUserAccount(userAccount);
                        resp.sendRedirect("/ASM2/user/list");
                    }
                } else if (action.equals("update")) {
                    if (name.isEmpty()) {
                        errors.add("Tên không được trống");
                    }
                    if (userName.isEmpty()) {
                        errors.add("Username không được trống");
                    }
                    if (PassWord.isEmpty()) {
                        errors.add("Password không được trống");
                    }

                    if (!errors.isEmpty()) {
                        req.setAttribute("errors", errors);
                        req.getRequestDispatcher("/Views/Viewsmanage.jsp").forward(req, resp);
                    } else {
                        UserAccount userAccount = new UserAccount(Integer.parseInt(id), name, userName, PassWord);
                        System.out.println("update id :" + id);
                        userdao.updateUserAccount(userAccount);
                        resp.sendRedirect("/ASM2/user/list");
                    }
                }
            }
        } else if (url.contains("update")) {
            if (islogin == false) {
                resp.sendRedirect(req.getContextPath() + "/user/login");
                return;
            }
            if (method.equals("GET")) {
                int ID = Integer.parseInt(req.getParameter("id"));
                System.out.println("UserAccountID update: " + ID);
                UserAccount userAccount = userdao.byUserAccountid(ID);
                System.out.println("userAccount" + userAccount);
                req.setAttribute("data", userAccount);
                System.out.println("data x :" + userAccount.getName());
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/Views/Viewsmanage.jsp").forward(req, resp);
            }
        } else if (url.contains("delete")) {
            if (method.equals("GET")) {
                int ID = Integer.parseInt(req.getParameter("id"));
                System.out.println("id delete + " + ID);
                userdao.bydeleteUserAccountid(ID);
                resp.sendRedirect("/ASM2/user/list");
            }
        } else if (url.contains("login")) {
            if (method.equals("GET")) {
                req.getRequestDispatcher("/Views/LoginViews.jsp").forward(req, resp);
            } else if (method.equals("POST")) {
                String username = req.getParameter("ip_usernamelogin");
                String password = req.getParameter("ip_passwordlogin");
                if (username.isEmpty()) {
                    errors.add("Username không được trống");
                }
                if (password.isEmpty()) {
                    errors.add("Password không được trống");
                }

                if (!errors.isEmpty()) {
                    req.setAttribute("errors", errors);
                    req.getRequestDispatcher("/Views/LoginViews.jsp").forward(req, resp); // Forward back to the login page with error messages
                } else {
                    UserAccount account = userdao.getUserByUsername(username);
                    if (account != null && username.equals(account.getUsername()) && password.equals(account.getPassword())) {
                        islogin = true;
                        resp.sendRedirect(req.getContextPath() + "/user/list");
                    } else {
                        islogin = false;
                        errors.add("Tên đăng nhập hoặc mật khẩu không đúng");
                        req.setAttribute("errors", errors);
                        req.getRequestDispatcher("/Views/LoginViews.jsp").forward(req, resp); 
                    }
                }
            }
        } else if (url.contains("hienthi")) {
            if (method.equals("GET")) {
                 if (islogin == false) {
                resp.sendRedirect(req.getContextPath() + "/user/login");
                return;
            }
                List<Customer> lstcus = (List<Customer>) req.getAttribute("Customers");
                for (Customer s : lstcus) {
                    // Lấy ID từ s.getUser_id()
                    int userId = s.getUser_id();
                    // Sử dụng UserDAO để lấy thông tin UserAccount từ ID
                    UserAccount user = userdao.getUserById(userId);
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + user);
                    // Set attribute với thông tin người dùng vào request
                    req.setAttribute("user_" + userId, user);
                }
                req.setAttribute("Customers", lstcus);
                req.getRequestDispatcher("/Views/ViewsCustomer.jsp").forward(req, resp);
            } else if (method.equals("POST")) {

            }
        } else if (url.contains("capnhap")) {
            if (method.equals("GET")) {
                 if (islogin == false) {
                resp.sendRedirect(req.getContextPath() + "/user/login");
                return;
            }
                List<Customer> lstcus = (List<Customer>) req.getAttribute("Customers");
                for (Customer s : lstcus) {
                    // Lấy ID từ s.getUser_id()
                    int userId = s.getUser_id();
                    // Sử dụng UserDAO để lấy thông tin UserAccount từ ID
                    UserAccount user = userdao.getUserById(userId);
                    System.out.println("bbbbbbbbbbbbbbbbbbbbbbb" + user);
//                    System.out.println("");
                    // Set attribute với thông tin người dùng vào request
                    req.setAttribute("user_" + userId, user);

                }
                int IDCustomer = Integer.parseInt(req.getParameter("id"));
                Customer customer = customerDAO.ByCustomerId(IDCustomer);
                req.setAttribute("customers", customer);
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/Views/ViewsCustomer.jsp").forward(req, resp);

            }
        } else if (url.contains("them")) {
            if (method.equals("POST")) {
                List<Customer> lstcus = (List<Customer>) req.getAttribute("Customers");
                for (Customer s : lstcus) {
                    // Lấy ID từ s.getUser_id()
                    int userId = s.getUser_id();
                    // Sử dụng UserDAO để lấy thông tin UserAccount từ ID
                    UserAccount user = userdao.getUserById(userId);
                    System.out.println("bbbbbbbbbbbbbbbbbbbbbbb" + user);
//                    System.out.println("");
                    // Set attribute với thông tin người dùng vào request
                    req.setAttribute("user_" + userId, user);

                }
                if (action.equals("them")) {
                    String namee = req.getParameter("ip_nameeee");
                    String Emailll = req.getParameter("ip_email");
                    if (namee.isEmpty()) {
                        errors.add("Username không được trống");
                    }
                    if (Emailll.isEmpty()) {
                        errors.add("Password không được trống");
                    } else if (!Emailll.trim().matches(".+@fpt\\.edu\\.vn")) {
                        errors.add("Email phải ở định dạng *@fpt.edu.vn");
                    }if (customerDAO.getUserByEmail(Emailll) != null) {
                        errors.add("Email đã tồn tại");
                    }

                    if (!errors.isEmpty()) {
                        req.setAttribute("errors", errors);
                        req.getRequestDispatcher("/Views/ViewsCustomer.jsp").forward(req, resp);
                    } else {
                        int selectbox = Integer.parseInt(req.getParameter("ip_selectbox"));
                        Customer customer = new Customer(namee, Emailll, selectbox);
                        customerDAO.AddCustomer(customer);
                        resp.sendRedirect("/ASM2/user/hienthi");

                    }
                } else if (action.equals("capnhap")) {
                    String idParam = req.getParameter("ip_id");
                    int idddd = 0; // Initialize to default value

                    // Check if the parameter is not empty and parse it
                    if (idParam != null && !idParam.isEmpty()) {
                        idddd = Integer.parseInt(idParam);
                    } else {
                        errors.add("Bạn chưa chọn Khách Hàng");
                    }

                    String namee = req.getParameter("ip_nameeee");
                    String Emailll = req.getParameter("ip_email");
                    int selectbox = Integer.parseInt(req.getParameter("ip_selectbox"));

                    if (namee.isEmpty()) {
                        errors.add("Tên không được trống");
                    }
                    if (Emailll.isEmpty()) {
                        errors.add("Email không được trống");
                    } else if (!Emailll.trim().matches(".+@fpt\\.edu\\.vn")) {
                        errors.add("Email phải ở định dạng *@fpt.edu.vn");
                    }

                    if (!errors.isEmpty()) {
                        req.setAttribute("errors", errors);
                        req.getRequestDispatcher("/Views/ViewsCustomer.jsp").forward(req, resp);
                    } else {
                        Customer customer = new Customer(idddd, namee, Emailll, selectbox);
                        customerDAO.UpdateCustomer(customer);
                        resp.sendRedirect(req.getContextPath() + "/user/hienthi");
                    }
                }
            }
            if (method.equals("GET")) {
                 if (islogin == false) {
                resp.sendRedirect(req.getContextPath() + "/user/login");
                return;
            }
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/Views/ViewsCustomer.jsp").forward(req, resp);
            }
        } else if (url.contains("xoa")) {
            if (method.equals("GET")) {
                int cusid = Integer.parseInt(req.getParameter("id"));
                Customer customer = customerDAO.ByCustomerId(cusid);
                customerDAO.DeleteCustomer(customer);
                resp.sendRedirect("/ASM2/user/hienthi");

            }
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserAccountServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserAccountServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
