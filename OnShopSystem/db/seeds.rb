# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the bin/rails db:seed command (or created alongside the database with db:setup).
#
# Examples:
#
#   movies = Movie.create([{ name: "Star Wars" }, { name: "Lord of the Rings" }])
#   Character.create(name: "Luke", movie: movies.first)
@admin = User.new(first_name: Rails.application.credentials.admin[:first_name],
                    last_name: Rails.application.credentials.admin[:last_name],
                    username: Rails.application.credentials.admin[:username] ,
                    address: Rails.application.credentials.admin[:address],
                    password: Rails.application.credentials.admin[:password],
                    email: Rails.application.credentials.admin[:email],
                    admin: true, telephone: Rails.application.credentials.admin[:telephone],
                    telephone_verified: false )

@admin.skip_confirmation!
@admin.confirm
@admin.save(validate: false)
a = User.find_by(email: Rails.application.credentials.admin[:email])
a.encrypted_password_copy = a.encrypted_password.dup
a.image.attach(io: File.open('app/assets/images/default-pic.png'), filename: 'no_profile_pic.png', content_type: 'image/png')
a.save(validate: false)


@user = User.new(first_name: Rails.application.credentials.user[:first_name],
                last_name: Rails.application.credentials.user[:last_name],
                username: Rails.application.credentials.user[:username],
                address: Rails.application.credentials.user[:address],
                password: Rails.application.credentials.user[:password],
                email: Rails.application.credentials.user[:email],
                telephone: Rails.application.credentials.user[:telephone],
                telephone_verified: false )
@user.skip_confirmation!
@user.confirm
@user.save(validate: false)
u = User.find_by(email: Rails.application.credentials.user[:email])
u.encrypted_password_copy = u.encrypted_password.dup
u.image.attach(io: File.open('app/assets/images/default-pic.png'), filename: 'no_profile_pic.png', content_type: 'image/png')
u.save(validate: false)


Product.create(product_name: "washing machine",                                                                      
description: "LG Automatic",                                                                          
image: "lgwashingmachine.png",                                                                        
product_quantity: 34,                                                                                 
color: "purple",                                                                                      
weight: 0.35e2,                                                                                       
mfg_date: 'Sat, 19 Nov 2022',                                                                           
exp_date: 'Fri, 15 Dec 2023',                                                                           
price: 29999)

Product.create(product_name: "ac",
description: "LG Automatic",
image: "lgac.png",
product_quantity: 23,
color: "white",
weight: 0.4e2,
mfg_date: 'Sun, 06 Nov 2022',
exp_date: 'Thu, 07 Feb 2030',
price: 24990)

Product.create(product_name: "generator",
description: "Tesla",
image: "teslagenerator.png",
product_quantity: 45,
color: "red",
weight: 0.5e2,
mfg_date: 'Thu, 08 Sep 2022',
exp_date: 'Mon, 31 Dec 2029',
price: 15000)


Product.create(product_name: "shirt",
description: "Shirt For Men",
image: "shirtmen.png",
product_quantity: 60,
color: "brown with black stripes",
weight: 0.3e0,
mfg_date: 'Thu, 03 Nov 2022',
exp_date: 'Fri, 15 Mar 2024',
price: 1299)

Product.create(product_name: "sweatshirt",
description: "T-Shirt For Women",
image: "tshirtwomen.png",
product_quantity: 72,
color: "pink with black sleeves",
weight: 0.5e0,
mfg_date: 'Sat, 13 Aug 2022',
exp_date: 'Sat, 01 Mar 2025',
price: 1499)

Product.create(product_name: "carrots",
description: "Eatables",
image: "carrots.png",
product_quantity: 40,
color: "orange",
weight: 0.1e1,
mfg_date: 'Wed, 16 Nov 2022',
exp_date: 'Fri, 18 Nov 2022',
price: 20)

Product.create(product_name: "bicycle",
description: "Vector 91",
image: "bicycle.png",
product_quantity: 20,
color: "grey",
weight: 0.15e2,
mfg_date: 'Sun, 02 Oct 2022',
exp_date: 'Tue, 08 Jun 2027',
price: 12000)

Product.create(product_name: "computer",
description: "Acer Gaming",
image: "computer.png",
product_quantity: 25,
color: "black",
weight: 0.5e2,
mfg_date: 'Wed, 29 Jun 2022',
exp_date: 'Fri, 14 Jun 2024',
price: 70000)

Product.create(product_name: "ipad",
description: "Apple iPad",
image: "ipad.png",
product_quantity: 10,
color: "grey , yellow , red , blue , black",
weight: 0.5e0,
mfg_date: 'Thu, 01 Dec 2022',
exp_date: 'Tue, 11 May 2027',
price: 41999)

Product.create(product_name: "jeans",
description: "Denim Jeans",
image: "jeans.png",
product_quantity: 35,
color: "dark blue",
weight: 0.8e0,
mfg_date: 'Wed, 16 Feb 2022',
exp_date: 'Wed, 15 Feb 2023',
price: 1499)

Product.create(product_name: "mouse",
description: "Alienware Mouse",
image: "mouse.png",
product_quantity: 27,
color: "grey",
weight: 0.4e0,
mfg_date: 'Mon, 26 Sep 2022',
exp_date: 'Fri, 20 Oct 2023',
price: 999)

Product.create(product_name: "onions",
description: "local onions",
image: "onions.png",
product_quantity: 50,
color: "brown",
weight: 0.1e1,
mfg_date: 'Sun, 04 Dec 2022',
exp_date: 'Sun, 18 Dec 2022',
price: 70)

Product.create(product_name: "popcorn",
description: "ACT II Popcorn",
image: "popcorn.png",
product_quantity: 150,
color: "white",
weight: 0.2e0,
mfg_date: 'Sat, 03 Dec 2022',
exp_date: 'Sat, 31 Dec 2022',
price: 150)

Product.create(product_name: "potatoes",
description: "Local Potatoes",
image: "potato.png",
product_quantity: 60,
color: "pale yellow",
weight: 0.1e1,
mfg_date: 'Sun, 04 Dec 2022',
exp_date: 'Sun, 18 Dec 2022',
price: 20)

Product.create(product_name: "radish",
description: "Eatables",
image: "radish.png",
product_quantity: 80,
color: "cherry and white",
weight: 0.1e1,
mfg_date: 'Fri, 02 Dec 2022',
exp_date: 'Wed, 14 Dec 2022',
price: 40)


Product.create(product_name: "laptop",
description: "Dell XPS 13",
image: "laptop.png",
product_quantity: 30,
color: "white , black",
weight: 0.5e0,
mfg_date: 'Thu, 15 Dec 2022',
exp_date: 'Tue, 20 May 2027',
price: 81999)

Product.create(product_name: "speaker",
description: "Modemista",
image: "speaker.png",
product_quantity: 15,
color: "black",
weight: 0.8e0,
mfg_date: 'Wed, 14 Feb 2022',
exp_date: 'Wed, 26 Feb 2023',
price: 2499)

Product.create(product_name: "router",
description: "TP-LINK Beast",
image: "router.png",
product_quantity: 27,
color: "black,red",
weight: 0.4e0,
mfg_date: 'Mon, 26 Nov 2022',
exp_date: 'Fri, 20 Oct 2024',
price: 29999)

Product.create(product_name: "hard disk",
description: "Toshiba 500 GB",
image: "hard disk.png",
product_quantity: 34,
color: "black",
weight: 0.1e1,
mfg_date: 'Sun, 04 Dec 2022',
exp_date: 'Sun, 18 Jan 2028',
price: 1500)

Product.create(product_name: "ram",
description: "Dell Ram 4x4 GB",
image: "ram.png",
product_quantity: 30,
color: "green",
weight: 0.2e0,
mfg_date: 'Sat, 16 Dec 2022',
exp_date: 'Sat, 31 Mar 2026',
price: 3500)

Product.create(product_name: "laptop battery",
description: "HP Battery",
image: "laptop battery.png",
product_quantity: 90,
color: "black",
weight: 0.1e1,
mfg_date: 'Sun, 04 Jun 2022',
exp_date: 'Sun, 28 Apr 2025',
price: 2100)

Product.create(product_name: "keyboard",
description: "Dell Keyboard",
image: "keyboard.png",
product_quantity: 47,
color: "black",
weight: 0.1e1,
mfg_date: 'Fri, 14 Dec 2022',
exp_date: 'Wed, 04 Aug 2024',
price: 899)

Product.create(product_name: "cd drive reader",
description: "Asus CD Drive Reader",
image: "cd drive reader.png",
product_quantity: 33,
color: "black",
weight: 0.1e1,
mfg_date: 'Fri, 27 Dec 2022',
exp_date: 'Wed, 04 Sep 2025',
price: 1299)

Product.create(product_name: "webcam",
description: "Lenovo Hd Webcam",
image: "webcam.png",
product_quantity: 23,
color: "black",
weight: 0.1e1,
mfg_date: 'Fri, 27 Feb 2022',
exp_date: 'Wed, 04 Sep 2025',
price: 1899)

