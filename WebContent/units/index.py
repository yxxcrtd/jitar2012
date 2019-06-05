class index:
    def execute(self):
        response.sendRedirect("../transfer.py?" + request.getQueryString())