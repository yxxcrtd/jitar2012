class go:
    def execute(self):
        q = request.getQueryString()
        response.sendRedirect(request.getContextPath() + "/go.action?" + q)