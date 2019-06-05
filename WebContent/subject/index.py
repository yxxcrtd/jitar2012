class index:
    def execute(self):
        response.sendRedirect("../subject.py?" + request.getQueryString())