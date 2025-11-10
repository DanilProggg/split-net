FROM nginx:1.27-alpine

COPY conf/nginx.conf /etc/nginx/nginx.conf

COPY static/ /usr/share/nginx/html/

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
