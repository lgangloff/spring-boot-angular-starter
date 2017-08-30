export class User{

    id: number;
    title:string;

    firstName:string;
    lastName:string;
    nickName:string;
    address:string;
    zipCode:string;
    city:string;
    telephonNumber:string;
    email:string;
    langKey:string;
    
    locked:boolean;    
    enabled:boolean;


    roles:Array<String> ;


    constructor(){
        this.title = "MR";
        this.roles = new Array();
        this.roles.push("ROLE_USER");
    }
}