require 'mysql2'
require 'digest/md5'
require 'fileutils'
host = '162.105.30.51'
con = Mysql2::Client.new(host: host, username: 'root', password: 'testing', database: 'ratetest')

file = File.open('results.txt', 'r')

20.times do
	class_uuid = file.gets.chomp
	samples = []
	con.query("INSERT INTO class(uuid, type) VALUES(UNHEX('#{class_uuid}'),'FINGERVEIN')")
	10.times do
		uuid, filename = file.gets.chomp.split(" ")
		filename = filename.split("\\")[-1]
		con.query("INSERT INTO sample(uuid, class_uuid, file) VALUES(UNHEX('#{uuid}'), UNHEX('#{class_uuid}'), '#{filename}')")
	end
end

file.close