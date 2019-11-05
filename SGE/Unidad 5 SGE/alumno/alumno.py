# -*- coding: utf-8 -*-
from osv import fields, osv

class alumno(osv.osv):
	_name = "alumno"
	_description = "Registro alumno"

	_columns = {
		'nombres' : fields.char('Nombres', size=64, required=True),
		'apellido_paterno' : fields.char('Apellido Paterno', size=64),
		'apellido_materno' : fields.char('Apellidos Materno', size=64),
		'activo' : fields.boolean('Activo'),
		'direccion' : fields.char('Dirección', size=100),
		'tel_cel' : fields.char('Tel./Cel.', size=100),
		'foto' : fields.binary('Foto'),
		'dni' : fields.char('D.N.I.', size=8),
		'fecha_nacimiento' : fields.date('Fecha de Nacimiento'),
		'nacionalidad' : fields.many2one('res.country', 'Nacionalidad'),
		'observaciones' : fields.text('Observaciones')
	}
	
alumno()

