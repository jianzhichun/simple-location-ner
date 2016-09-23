# -*- coding: UTF-8 -*- 

import json,collections,codecs

formatdic = collections.defaultdict()
with codecs.open('location.json','r','utf8') as f:
    lis = json.load(f)
    i = 0
    for p in lis:
        formatdic[i]=p['name'].replace(' ', '')+',province'
        pcode = i
        i+=1
        for c in p['city']:
            if c['name'] == u'其他':continue
            formatdic[i]=c['name'].replace(' ', '')+',city,'+str(pcode)
            ccode = i
            i+=1
            for a in c['area']:
                if a == u'其他':
                    continue
                formatdic[i]=a.replace(' ', '')+',area,'+str(ccode)
                i+=1
with codecs.open('location.dat','w','utf8') as f:
    for k,v in formatdic.items():
        f.write(str(k)+','+v+'\n')