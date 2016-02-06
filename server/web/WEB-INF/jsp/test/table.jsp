<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
<!--
.self-pagination{
    float: right;
    margin: 0;
}
-->
</style>
<div class="container">
    <table class="table table-bordered table-hover table-striped">
        <thead>
        <tr>
            <th><input type="checkbox" value=""></th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Username</th>
            <th>Username1</th>
            <th>Username2</th>
            <th>Username3</th>
            <th>Username4</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row"><input type="checkbox" value="1"></th>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>
            <td>Mark</td>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>
        </tr>
        <tr>
            <th scope="row"><input type="checkbox" value="2"></th>
            <td>Jacob</td>
            <td>Thornton</td>
            <td>@fat</td>
            <td>Mark</td>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>
        </tr>
        <tr>
            <th scope="row"><input type="checkbox" value="3"></th>
            <td>Larry</td>
            <td>the Bird</td>
            <td>@twitter</td>
            <td>Mark</td>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>

        </tr>
        </tbody>
    </table>

    <nav>
        <ul class="self-pagination pagination">
            <li>
                <a href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <li><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li>
                <a href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</div>
