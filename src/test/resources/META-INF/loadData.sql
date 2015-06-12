-- Add People
insert into person (family_name, given_name, middle_name, user_id) values ('Clark', 'Mary', 'Higgins', null);
insert into person (family_name, given_name, middle_name, user_id) values ('Fox', 'Michael', 'J', null);
insert into person (family_name, given_name, middle_name, user_id) values ('Poe', 'Edgar', 'Allan', null);
insert into person (family_name, given_name, middle_name, user_id) values ('Rowling', 'J', 'K', null);
insert into person (family_name, given_name, middle_name, user_id) values ('Shaw', 'George', 'Bernard', null);
insert into person (family_name, given_name, middle_name, user_id) values ('Longfellow', 'Henry', 'Wadsworth', null);
insert into person (family_name, given_name, middle_name, user_id) values ('Sartre', 'Jean', 'Paul', null);
insert into person (family_name, given_name, middle_name, user_id) values ('Thoreau', 'Henry', 'David', null);

-- Add Numbers
insert into handset_number (phone_number, sim_id) values ('+16472916602', '12345678901234567890');
insert into handset_number (phone_number, sim_id) values ('+19706317100', '12345678901234567890');
insert into handset_number (phone_number, sim_id) values ('+13038471261', '12345678901234567890');
insert into handset_number (phone_number, sim_id) values ('+13035551212', '12345678901234567890');
insert into handset_number (phone_number, sim_id) values ('+18005551212', '12345678901234567890');
insert into handset_number (phone_number, sim_id) values ('+13036326543', '12345678901234567890');
insert into handset_number (phone_number, sim_id) values ('+12892362542', '12345678901234567890');

-- Add Statuses
insert into handset_status (status, description) values (101, 'Unlocked');
insert into handset_status (status, description) values (102, 'Locked');
insert into handset_status (status, description) values (103, 'Lost');

-- Add Handsets
insert into handset (device_id, status_status) values ('017593754710123', 101);
insert into handset (device_id, status_status) values ('654321357087683', 102);
insert into handset (device_id, status_status) values ('046304687900130', 103);
insert into handset (device_id, status_status) values ('065026060305998', 101);
insert into handset (device_id, status_status) values ('747381010934784', 101);
insert into handset (device_id, status_status) values ('829304209475710', 102);
insert into handset (device_id, status_status) values ('086421357904628', 101);

-- Assign Numbers to Handsets
update handset set number_id = (select id from handset_number where phone_number='+16472916602'), last_update=current_timestamp where device_id = '017593754710123';
update handset set number_id = (select id from handset_number where phone_number='+19706317100'), last_update=current_timestamp where device_id = '654321357087683';
update handset set number_id = (select id from handset_number where phone_number='+13038471261'), last_update=current_timestamp where device_id = '046304687900130';
update handset set number_id = (select id from handset_number where phone_number='+13035551212'), last_update=current_timestamp where device_id = '065026060305998';
update handset set number_id = (select id from handset_number where phone_number='+18005551212'), last_update=current_timestamp where device_id = '747381010934784';
update handset set number_id = (select id from handset_number where phone_number='+13036326543'), last_update=current_timestamp where device_id = '829304209475710';
update handset set number_id = (select id from handset_number where phone_number='+12892362542'), last_update=current_timestamp where device_id = '086421357904628';

-- Add Actions
insert into handset_action (action, message, argument, default_value) values ('Send Simple Text Message',   '!simple!', 'false', null);
insert into handset_action (action, message, argument, default_value) values ('Lock Device',                '-lock', 'false', null);
insert into handset_action (action, message, argument, default_value) values ('Get Current Location',       '-gps', 'false', null);
insert into handset_action (action, message, argument, default_value) values ('Reply to SMS',               '-sms', 'false', null);
insert into handset_action (action, message, argument, default_value) values ('Verify SIM Card',            '-check-sim', 'false', null);
insert into handset_action (action, message, argument, default_value) values ('Hold with Alarm',            '-hold-lock', 'false', null);
insert into handset_action (action, message, argument, default_value) values ('Hold Device with Message',   '-hold', 'true', 'Please hold while we reset your device.');
insert into handset_action (action, message, argument, default_value) values ('Stop Holding the Device',    '-stop-hold', 'false', null);
insert into handset_action (action, message, argument, default_value) values ('Edit AccessMRS Preferences', '-accessmrs-pref', 'true', 'preference_name=value');
insert into handset_action (action, message, argument, default_value) values ('Reset Password to Default',  '-reset-pwd', 'true', '12345');
insert into handset_action (action, message, argument, default_value) values ('Factory Reset',              '-reset', 'false', null);
insert into handset_action (action, message, argument, default_value) values ('Wipe All Patient Data',      '-wipe', 'false', null);
insert into handset_action (action, message, argument, default_value) values ('Cancel Device Admin Alarms', '-cancel', 'false', null);
insert into handset_action (action, message, argument, default_value) values ('Reset Unique Device ID',     'create.new.admin.id.and.sms.to.admin', 'false', null);
insert into handset_action (action, message, argument, default_value) values ('Lock with Secure Password',  'create.new.password.and.sms.to.admin', 'false', null);

-- Add Message Types
insert into message_type (type, description) values ('GPS', 'GPS Location');
insert into message_type (type, description) values ('Status', 'Status Update Message');
insert into message_type (type, description) values ('DeviceAlarm', 'Device Alarm Status');
insert into message_type (type, description) values ('Password', 'Password Status Message');
insert into message_type (type, description) values ('NewSIM', 'New SIM ID Message');

-- Add Handset Reply Patterns
insert into handset_reply (messagetype_type, pattern) values ('GPS', 'GPS=');
insert into handset_reply (messagetype_type, pattern) values ('Status', 'Status=');
insert into handset_reply (messagetype_type, pattern) values ('DeviceAlarm', 'DeviceAlarm=');
insert into handset_reply (messagetype_type, pattern) values ('Password', 'Password=');
insert into handset_reply (messagetype_type, pattern) values ('NewSIM', 'NewSim=');

-- Add Users (that can login to app)
insert into app_user (username, password, name, email, consistencyVersion) values ('adrian', '891660b247576a5fcc18b53e8ba87fb232845d5', 'Adrian Schauer', 'adrian.schauer@jda.com', 1);
insert into app_user (username, password, name, email, consistencyVersion) values ('ashley', '891660b247576a5fcc18b53e8ba87fb232845d5', 'Ashley Barey', 'ashley.barey@jda.com', 1);
insert into app_user (username, password, name, email, consistencyVersion) values ('erik', '891660b247576a5fcc18b53e8ba87fb232845d5', 'Erik Rotaru', 'erik.rotaru@jda.com', 1);
insert into app_user (username, password, name, email, consistencyVersion) values ('greg', '891660b247576a5fcc18b53e8ba87fb232845d5', 'Greg Sachro', 'greg.sachro@jda.com', 1);
insert into app_user (username, password, name, email, consistencyVersion) values ('louis', '891660b247576a5fcc18b53e8ba87fb232845d5', 'Louis Fazen', 'louis.fazen@gmail.com', 1);
insert into app_user (username, password, name, email, consistencyVersion) values ('noam', '891660b247576a5fcc18b53e8ba87fb232845d5', 'Noam Krendel', 'noam.krendel@jda.com', 1);
