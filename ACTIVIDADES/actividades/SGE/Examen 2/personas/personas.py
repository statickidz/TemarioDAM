# -*- encoding: utf-8 -*-

from openerp.osv import osv, fields
from openerp.tools.translate import _

class personas(osv.osv):
	# Nombre del modelo
    _name = 'personas'

    # Columnas del modelo
    _columns = {        
	    'name': fields.char('Name', size=100, required=True),
	    'lastname': fields.char('Lastname', size=100),
	    'street': fields.char('Street', size=100),
		'telephone': fields.char('Telephone', size=30),
		'gender': fields.selection((('M','Male'),('F','Female')), 'Gender'),
    }
personas()