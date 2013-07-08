require 'mysql2'
require 'digest/md5'
require 'fileutils'
host = '162.105.30.51'
con = Mysql2::Client.new(host: host, username: 'root', password: 'testing', database: 'rate3')
SAMPLE_ROOT = 'G:/samples'

results = {}
cnt = 0

q = "SELECT HEX(uuid) AS uuid FROM class ORDER BY RAND() LIMIT 200"

con.query(q).each do |row|
	break if cnt == 20
	q = "SELECT HEX(uuid) AS uuid, file FROM sample where HEX(class_uuid)='#{row["uuid"]}' ORDER BY RAND() LIMIT 10"
	samples = con.query(q).to_a
	next if samples.size < 10
	results[row["uuid"]] = samples
	cnt += 1
	puts cnt
end

file = File.open("results.txt", "w+") do |file|
	results.each do |class_uuid, samples|
		file.puts class_uuid
		puts class_uuid
		samples.each do |sample|
			file.puts sample["uuid"] + " " + sample["file"]
			puts sample['uuid']
		end
	end
end

File.open("results.txt", "r") do |file|
	20.times do
		class_uuid = file.gets
		10.times do 
			begin
				uuid, filename = file.gets.chomp.split(" ")
				fullname = SAMPLE_ROOT + "/" + filename
				FileUtils.cp fullname, "./"
			rescue Exception => e
				puts e
			end
		end
	end
end
