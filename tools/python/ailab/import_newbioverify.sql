insert ignore into person (uuid,name,gender,birth) select student_uuid,name,gender,birth_date from newbioverify.student;

#delete from class where import_tag = 'newbioverify';

insert ignore into class (uuid,person_uuid,type,created,import_tag) 
	select finger_uuid,student_uuid,'FINGERVEIN',created_time,'newbioverify' from newbioverify.finger;

#delete from sample where import_tag = 'newbioverify';

insert ignore into sample (uuid,class_uuid,created,file,device_type,device,import_tag) 
	select sample_uuid,finger_uuid,created_time,
	concat("newbioverify/", image_filename),unhex("DEC610419B4611E284CB002197642BD1"),
	device_uuid, 'newbioverify' from newbioverify.sample;


# after import, we should run import_newbioverify.py to check the files and remove duplicated samples and .etc
