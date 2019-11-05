# -*- encoding: utf-8 -*-
from osv import osv, fields
class milibro3(osv.osv): 
    _name = 'milibro' 
    _inherit = 'milibro' 
    _columns = {
        'nombre': fields.char('Name', size=150),
        'isbn': fields.char('ISBN', size=15),
        'precio': fields.float('Price',digits=(4,2)),
        'resumen': fields.text('Description'),
        'fecha': fields.date('Date'),
        'revisado': fields.boolean('Revised'),
        'aprobado': fields.selection((('S','Yes'),('N','No'),('P','Pending')),'Aprobed')
    }
milibro3()
