use rate3;

insert ignore into person (uuid,name,gender,birth) select student_uuid,name,gender,birth_date from newbioverify.student;

update person set name = (select name from newbioverify.student where person.uuid=newbioverify.student.student_uuid);
update person set gender = (select gender from newbioverify.student where person.uuid=newbioverify.student.student_uuid);
update person set birth = (select birth_date from newbioverify.student where person.uuid=newbioverify.student.student_uuid);

#delete from class where import_tag = 'newbioverify';

insert ignore into class (uuid,person_uuid,type,created,import_tag) 
	select finger_uuid,student_uuid,'FINGERVEIN',created_time,'newbioverify' from newbioverify.finger;

#delete from sample where import_tag = 'newbioverify';

insert ignore into device_type (uuid, name, type, provider, version) values (unhex("DEC610419B4611E284CB002197642BD1"), "YANNAN V5 VeinSensor", "FINGERVEIN", "YANNAN TECH", "V5");

# 通过left join去除finger不存在的sample 
# 去除未校准的设备 
# 去除重复的image_filename
insert ignore into sample 
	(uuid,class_uuid,created,file,device_type,device,import_tag) 
	select 
		sample_uuid,finger.finger_uuid,sample.created_time, concat("newbioverify/", image_filename),unhex("DEC610419B4611E284CB002197642BD1"), device_uuid, 'newbioverify' 
	from 
		newbioverify.sample left join newbioverify.finger on newbioverify.sample.finger_uuid=newbioverify.finger.finger_uuid
	where 
		newbioverify.sample.device_uuid != unhex("0A66B7775EC711E2A73200247E0F739B")
		and newbioverify.finger.finger_uuid != unhex("00000000000000000000000000000000")
	group by 
		image_filename
;

# after import, we should run import_newbioverify.py to check the files and remove duplicated samples and .etc
