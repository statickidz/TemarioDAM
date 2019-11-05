# -*- encoding: utf-8 -*-
from osv import osv, fields
class milibro2_categorias(osv.osv): 
    _name = 'milibro2.categorias' 
    _columns = {'name': fields.char('Descripcion', size=150,required=True)}
milibro2_categorias()

class milibro2(osv.osv): 
    _name = 'milibro2' 
    _inherit = 'milibro' 
    _columns = {
        'isbn': fields.char('ISBN', size=15),
        'precio': fields.float('Price',digits=(4,2)),
        'resumen': fields.text('Description'),
        'fecha': fields.date('Date'),
        'revisado': fields.boolean('Revisado'),
        'aprobado': fields.selection((('S','Si'),('N','No'),('P','Pendiente')),'Aprobed'), 
        'categoria': fields.many2one('milibro2.categorias','Category',ondelete='cascade')
    }
milibro2()

