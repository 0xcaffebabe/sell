package wang.ismy.sell.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 接口统一返回对象
 * @author MY
 * @date 2020/2/2 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -6849794470754667711L;

    private Integer code;

    private String msg;

    private T data;

    public static <R>Result<R>  success(R data){
        Result<R> result = new Result<>();
        result.setCode(0);
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    public static Result success(){
        return success(null);
    }

    public static <R>Result<R>  error(R data){
        Result<R> result = new Result<>();
        result.setCode(1);
        result.setMsg("error");
        result.setData(data);
        return result;
    }

    public static Result error(){
        return error(null);
    }
}
