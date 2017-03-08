# coding:utf8

import sys
reload(sys)
sys.setdefaultencoding('utf8')

import urllib2
import urllib

import json
from bs4 import BeautifulSoup

# GET
url = "https://movie.douban.com/j/search_tags?type=movie"
request = urllib2.Request(url=url)
response = urllib2.urlopen(request, timeout=20)
result = json.loads(response.read())

tags = result["tags"]

movies = []

for tag in tags:
	pageStart = 0
	while 1:
		url = "https://movie.douban.com/j/search_subjects?type=movie&tag="+tag+"&sort=recommend&page_limit=20&page_start="+str(pageStart)

		print url
		request = urllib2.Request(url=url)
		response = urllib2.urlopen(request, timeout=20)
		result = json.loads(response.read())

		result = result["subjects"]

		if len(result) == 0:
			break
		pageStart += 20;

		for item in result:
			movies.append(item)
		break
fw = open("movie.csv","w")
temp = "名称,评分\n"
fw.write(temp)

for x in xrange(0,len(movies)):
	item = movies[x]

	request = urllib2.Request(url=item["url"])
	response = urllib2.urlopen(request, timeout=20)
	result = response.read()

	html = BeautifulSoup(result)
	title = html.select('h1')[0]
	title = title.select('span')[0]
	title = title.get_text()

	rate = html.select(".rating_num")
	rate = rate[0].get_text()
	temp = title+","+str(rate)+"\n"
	print temp
	fw.write(temp)

fw.close()


