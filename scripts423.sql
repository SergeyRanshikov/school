select  student.name, student.age, faculty.name
from student
         inner join faculty on student.faculty_id = faculty.id

select  student.name
from student
         inner join avatar on student.avatar_id = avatar.id