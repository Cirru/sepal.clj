
def demo $ {}
  :a 1
  :b $ [] 2
  :c $ {} (:d 4)

get demo :a

:a demo
