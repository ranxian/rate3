require 'mysql2'
require 'digest/md5'
require 'benchmark'
def work(pid, _start, _end)
	host = '162.105.30.162'
	localhost = '127.0.0.1'
	con = Mysql2::Client.new(host: host, username: 'root',
	password: 'testing', database: 'rate3')

	results = con.query("SELECT HEX(uuid), file FROM sample where md5=unhex('00000000000000000000000000000000') LIMIT #{_start}, #{_end} ", symbolize_keys: true)

	# SAMPLE_ROOT = "G:/RATE_ROOT/samples/"
	results.each do |result|

		sample_path = SAMPLE_ROOT + result[:file]

		begin
			md5val = Digest::MD5.hexdigest(IO.binread(sample_path))
			q = "UPDATE sample SET md5=0x#{md5val} WHERE uuid=0x#{result[:"HEX(uuid)"]}"
			con.query(q)
		rescue
		end
		puts "thread[#{pid}] #{result[:file]} added md5"
	end
end

SAMPLE_ROOT = "G:/RATE_ROOT/samples/"

threads = []
(0..35).each do |num|
	threads.push(Thread.new do
		puts "this is thread #" + num.to_s
		work(num, 20000 * num, 20000 * (num+1))
	end)
end

cnt = 0
threads.first.join

threads.each do |t|
	cnt += 1
	puts "new thread #{cnt}"
	t.join
end