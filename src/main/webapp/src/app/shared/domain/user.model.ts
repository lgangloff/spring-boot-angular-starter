export class User{

    id: number;
    private title:string;

    private firstName:string;
    private lastName:string;
    private nickName:string;
    private address:string;
    private zipCode:string;
    private city:string;
    private telephonNumber:string;
    private email:string;
    private langKey:string;
    
    private locked:boolean;    
    private enabled:boolean;


    private roles:Array<String> ;


    constructor(){
    }
}